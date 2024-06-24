package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.domain.PixResponse;

public interface PixClientRest {
    PixResponse createCharge(PaymentRequest paymentRequest);
}
