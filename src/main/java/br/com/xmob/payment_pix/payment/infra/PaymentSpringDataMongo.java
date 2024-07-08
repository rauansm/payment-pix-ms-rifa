package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PaymentSpringDataMongo extends MongoRepository<Payment,Long> {
    List<Payment> findAllByIntegratedIsFalse();

    List<Payment> findAllByStatusAndIntegratedIsTrue(String status);
}
