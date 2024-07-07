package br.com.xmob.payment_pix.payment.domain;

public interface PaymentStatusNotifier {
    void notifyMSOrder(PaymentStatusDTO payment, String exchange, String routingKey);
}
