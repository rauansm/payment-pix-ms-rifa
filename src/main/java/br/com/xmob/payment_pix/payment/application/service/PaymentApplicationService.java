package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.api.PaymentResponse;
import br.com.xmob.payment_pix.payment.domain.PixResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentApplicationService implements PaymentService {

    private final PixClientRest pixClientRest;

    @Override
    public PaymentResponse createCharge(PaymentRequest paymentRequest) {
        log.info("[start] PaymentApplicationService - createCharge");
        log.debug("[PaymentRequest] {}", paymentRequest);
        PixResponse pixResponse = pixClientRest.createCharge(paymentRequest);
        log.info("[finish] PaymentApplicationService - createCharge");
        return null;
    }
}
