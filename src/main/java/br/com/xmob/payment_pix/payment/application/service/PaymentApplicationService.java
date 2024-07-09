package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.config.RabbitMQProperties;
import br.com.xmob.payment_pix.payment.application.api.PaymentEvent;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.api.PaymentResponse;
import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.payment.domain.PaymentStatusDTO;
import br.com.xmob.payment_pix.payment.domain.PaymentStatusNotifier;
import br.com.xmob.payment_pix.payment.infra.PaymentRepository;
import br.com.xmob.payment_pix.pix.application.service.PixService;
import br.com.xmob.payment_pix.pix.domain.PixResponse;
import br.com.xmob.payment_pix.pix.domain.PixStatusResponse;
import br.com.xmob.payment_pix.strategy.factory.RoutingKeyFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentApplicationService implements PaymentService {

    private final PixService pixService;
    private final PaymentRepository paymentRepository;
    private final PaymentStatusNotifier paymentStatusNotifier;
    private final RabbitMQProperties rabbitmqProperties;
    private final RoutingKeyFactory routingKeyFactory;

    @Override
    public PaymentResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PaymentApplicationService - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PixResponse pixResponse = pixService.createCharge(paymentRequest);
        Payment payment = paymentRepository.save(new Payment(pixResponse,paymentRequest));
        log.info("[finish] PaymentApplicationService - createCharge");
        return new PaymentResponse(payment);
    }

    @Override
    public void paymentUpdate(PaymentEvent paymentEvent) {
        log.info("[start] PaymentApplicationService - paymentUpdate");
        log.debug("[PaymentEvent] {}", paymentEvent);
        Payment payment = paymentRepository.searchPaymentById(paymentEvent.getData().getId());
        PixStatusResponse statusPayment = pixService.searchPixPaymentStatus(payment);
        payment.updateStatus(statusPayment);
        paymentRepository.save(payment);
        notifyStatusPaymentRabbitMQ(payment);
        log.info("[finish] PaymentApplicationService - paymentUpdate");
    }

    public void notifyStatusPaymentRabbitMQ(Payment payment) {
        log.info("[start] PaymentApplicationService - notifyStatusPaymentRabbitMQ");
        try {
            String routingKey = routingKeyFactory.getStrategy(payment.getStatus()).determineRoutingKeyByStatus();
            PaymentStatusDTO paymentStatusDTO = new PaymentStatusDTO(payment);
            paymentStatusNotifier.notifyOrder(paymentStatusDTO, rabbitmqProperties.getPaymentStatusExchange(), routingKey);
        } catch (RuntimeException ex){
            payment.markAsNotIntegrated();
            paymentRepository.save(payment);
            log.error("[Error] ", ex);
        }
        log.info("[finish] PaymentApplicationService - notifyStatusPaymentRabbitMQ");
    }
}
