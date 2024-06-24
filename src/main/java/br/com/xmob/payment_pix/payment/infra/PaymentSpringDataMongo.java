package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.Payment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PaymentSpringDataMongo extends MongoRepository<Payment,Long> {
}
