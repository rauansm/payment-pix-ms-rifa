package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.Payment;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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

    @Override
    public Payment searchPaymentById(String id) {
        log.info("[start] PaymentInfraRepository - searchPaymentById");
        Optional<Payment> payment = paymentSpringDataMongo.findById(Long.valueOf(id));
        log.info("[finish] PaymentInfraRepository - searchPaymentById");
        return payment.orElseThrow((() -> new RuntimeException("Pagamento não encontrado!")));
    }

    @Override
    public List<Payment> searchForPaymentsWithoutIntegrated() {
        log.info("[start] PaymentInfraRepository - searchForPaymentsWithoutIntegrated");
        List<Payment> paymentsNotIntegrated = paymentSpringDataMongo.findAllByIntegratedIsFalse();
        log.info("[finish] PaymentInfraRepository - searchForPaymentsWithoutIntegrated");
        return paymentsNotIntegrated;
    }
}
