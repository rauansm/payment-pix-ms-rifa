package br.com.xmob.payment_pix.payment.infra;

import br.com.xmob.payment_pix.payment.domain.PaymentStatusNotifier;
import br.com.xmob.payment_pix.payment.domain.PaymentStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@Log4j2
@RequiredArgsConstructor
public class PaymentStatusNotifierInfraRabbit implements PaymentStatusNotifier {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void notifyOrder(PaymentStatusDTO paymentStatus, String exchange, String routingKey) {
        log.info("[start] PaymentNotificationApplicationService - notifyMSOrder");
        rabbitTemplate.convertAndSend(exchange,routingKey,paymentStatus);
        log.info("[finish] PaymentNotificationApplicationService - notifyMSOrder");
    }
}
