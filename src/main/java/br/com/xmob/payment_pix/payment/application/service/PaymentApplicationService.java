package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentEvent;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.api.PaymentResponse;
import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.payment.domain.PixResponse;
import br.com.xmob.payment_pix.payment.infra.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentApplicationService implements PaymentService {

    private final PixClientRest pixClientRest;
    private final PaymentRepository paymentRepository;

    @Override
    public PaymentResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PaymentApplicationService - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PixResponse pixResponse = pixClientRest.createCharge(paymentRequest);
        Payment payment = paymentRepository.save(new Payment(pixResponse,paymentRequest));
        log.info("[finish] PaymentApplicationService - createCharge");
        return new PaymentResponse(payment);
    }

    @Override
    public void paymentUpdate(PaymentEvent paymentEvent) {
        log.info("[start] PaymentApplicationService - paymentUpdate");
        log.debug("[PaymentEvent] {}", paymentEvent);

        log.info("[finish] PaymentApplicationService - paymentUpdate");
    }
}
