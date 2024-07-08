package br.com.xmob.payment_pix.pix.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.domain.Payment;
import br.com.xmob.payment_pix.pix.domain.PixResponse;
import br.com.xmob.payment_pix.pix.domain.PixStatusResponse;

public interface PixService {
    PixResponse createCharge(PaymentRequest paymentRequest);

    PixStatusResponse searchPixPaymentStatus(Payment payment);

    void markPixPaymentAsExpired(Long paymentId, String cancelled);
}
