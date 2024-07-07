package br.com.xmob.payment_pix.payment.domain;

public interface PaymentStatusNotifier {
    void notifyOrder(PaymentStatusDTO paymentStatus, String exchange, String routingKey);
}
