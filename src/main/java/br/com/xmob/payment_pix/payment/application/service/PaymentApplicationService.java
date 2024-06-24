package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.api.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentApplicationService implements PaymentService {

    @Override
    public PaymentResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PaymentApplicationService - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);

        log.info("[finish] PaymentApplicationService - createCharge");
        return null;
    }
}
