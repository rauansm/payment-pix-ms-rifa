package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.application.api.PaymentEvent;
import br.com.xmob.payment_pix.payment.application.api.PaymentRequest;
import br.com.xmob.payment_pix.payment.application.api.PaymentResponse;
import br.com.xmob.payment_pix.payment.domain.Payment;

public interface PaymentService {
    PaymentResponse createCharge(PaymentRequest paymentRequest);

    void paymentUpdate(PaymentEvent paymentEvent);

    void notifyStatusPaymentRabbitMQ(Payment payment);
}
