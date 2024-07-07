package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.Payment;

import java.util.List;

public interface PaymentRepository {
    Payment save(Payment payment);

    Payment searchPaymentById(String id);

    List<Payment> searchForPaymentsWithoutIntegrated();
}
