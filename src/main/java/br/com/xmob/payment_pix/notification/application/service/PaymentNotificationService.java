package br.com.xmob.payment_pix.notification.application.service;

import br.com.xmob.payment_pix.payment.application.service.PaymentStatusDTO;

public interface PaymentNotificationService {
    void notifyMSOrder(PaymentStatusDTO payment, String exchange, String routingKey);
}
