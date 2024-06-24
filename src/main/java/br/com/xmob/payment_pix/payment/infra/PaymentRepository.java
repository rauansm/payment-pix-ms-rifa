package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.Payment;

public interface PaymentRepository {
    Payment save(Payment payment);
}
