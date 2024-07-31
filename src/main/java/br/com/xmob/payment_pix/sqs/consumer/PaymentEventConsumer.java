package br.com.xmob.payment_pix.sqs.consumer;

import br.com.xmob.payment_pix.payment.application.service.PaymentService;
import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Log4j2
@Component
@RequiredArgsConstructor
public class PaymentEventConsumer {
    private final PaymentService paymentService;

    @SqsListener(value = "${webhook.pix.name}")
    public void paymentEventWebhook(@Payload PaymentEvent paymentEvent){
        log.info("[start] PaymentEventConsumer - paymentEventWebhook");
        log.debug("[PaymentEvent] {}", paymentEvent);
        paymentService.paymentUpdate(paymentEvent);
        log.info("[finish] PaymentEventConsumer - paymentEventWebhook");
    }
}
