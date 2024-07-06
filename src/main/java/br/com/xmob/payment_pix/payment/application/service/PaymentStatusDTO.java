package br.com.xmob.payment_pix.payment.application.service;

import br.com.xmob.payment_pix.payment.domain.Payment;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
public class PaymentStatusDTO {
    private UUID orderId;
    private String status;

    public PaymentStatusDTO(Payment payment) {
        this.orderId = payment.getOrderId();
        this.status = payment.getStatus();
    }
}
