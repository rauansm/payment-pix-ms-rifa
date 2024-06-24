package br.com.xmob.payment_pix.payment.application.api;

import br.com.xmob.payment_pix.payment.domain.Payment;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class PaymentResponse {
    private String paymentPIXCopiaCola;
    private String paymentPIXQRCode;

    public PaymentResponse(Payment payment) {
        this.paymentPIXCopiaCola = payment.getCopiaCola();
        this.paymentPIXQRCode = payment.getQrCode();
    }
}
