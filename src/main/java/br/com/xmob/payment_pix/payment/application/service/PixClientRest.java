package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentEvent;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.domain.PixResponse;
import br.com.xmob.payment_pix.payment.domain.PixStatusResponse;

public interface PixClientRest {
    PixResponse createCharge(PaymentRequest paymentRequest);

    PixStatusResponse searchPixPaymentStatus(PaymentEvent paymentEvent);
}
