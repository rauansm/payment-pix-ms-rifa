package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

@Repository
@Log4j2
@RequiredArgsConstructor
public class PaymentInfraRepository implements PaymentRepository {

    private final PaymentSpringDataMongo paymentSpringDataMongo;

    @Override
    public Payment save(Payment payment) {
        log.info("[start] PaymentInfraRepository - save");
        log.debug("[Payment] {}", payment);
        paymentSpringDataMongo.save(payment);
        log.info("[finish] PaymentInfraRepository - save");
        return payment;
    }
}
