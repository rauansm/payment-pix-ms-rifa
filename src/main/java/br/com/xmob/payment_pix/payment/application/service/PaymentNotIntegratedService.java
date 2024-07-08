package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.payment.infra.PaymentRepository;
import br.com.xmob.payment_pix.pix.application.service.PixService;
import br.com.xmob.payment_pix.pix.domain.PixStatusResponse;
import br.com.xmob.payment_pix.utils.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Log4j2
public class PaymentNotIntegratedService {
    private final PaymentRepository paymentRepository;
    private final PaymentService paymentService;
    private final PixService pixService;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void searchForPaymentWithoutIntegration() {
        log.debug("[start] PaymentNotIntegratedService - searchForPaymentWithoutIntegration");
        List<Payment> paymentsNotIntegrated = paymentRepository.searchForPaymentsWithoutIntegrated();
        log.debug("[Payments] {}", paymentsNotIntegrated);
        paymentsNotIntegrated.forEach(payment -> {
            if (!payment.getStatus().equals(PaymentStatus.PENDING)) {
                handleIntegratedPayment(payment);
            } else {
                handleIntegrationFailure(payment);
            }
        });
        log.debug("[finish] PaymentNotIntegratedService - searchForPaymentWithoutIntegration");
    }

    private void handleIntegratedPayment(Payment payment) {
        log.debug("[start] PaymentNotIntegratedService - handleIntegratedPayment");
        payment.markAsIntegrated();
        paymentRepository.save(payment);
        paymentService.notifyStatusPaymentRabbitMQ(payment);
        log.debug("[finish] PaymentNotIntegratedService - handleIntegratedPayment");
    }

    private void handleIntegrationFailure(Payment payment) {
        log.debug("[start] PaymentNotIntegratedService - handleIntegrationFailure");
        PixStatusResponse statusPayment = pixService.searchPixPaymentStatus(payment);
        payment.updateStatus(statusPayment);
        payment.markAsIntegrated();
        paymentRepository.save(payment);
        paymentService.notifyStatusPaymentRabbitMQ(payment);
        log.debug("[finish] PaymentNotIntegratedService - handleIntegrationFailure");
    }
}
