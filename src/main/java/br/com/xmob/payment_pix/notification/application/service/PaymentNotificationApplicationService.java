package br.com.xmob.payment_pix.notification.application.service;

import br.com.xmob.payment_pix.payment.application.service.PaymentStatusDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentNotificationApplicationService implements PaymentNotificationService {
    private final RabbitTemplate rabbitTemplate;

    @Override
    public void notifyMSOrder(PaymentStatusDTO payment, String exchange, String routingKey) {
        log.info("[start] PaymentNotificationApplicationService - notifyMSOrder");
        rabbitTemplate.convertAndSend(exchange,routingKey,payment);
        log.info("[finish] PaymentNotificationApplicationService - notifyMSOrder");
    }
}
