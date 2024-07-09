package br.com.xmob.payment_pix.strategy.impl;

import br.com.xmob.payment_pix.config.RabbitMQProperties;
import br.com.xmob.payment_pix.strategy.RoutingKeyStrategy;
import br.com.xmob.payment_pix.utils.PaymentStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoutingKeyCancelled implements RoutingKeyStrategy {
    private final RabbitMQProperties rabbitMQProperties;

    @Override
    public String determineRoutingKeyByStatus() {
        return rabbitMQProperties.getPaymentExpiredRoutingKey();
    }

    @Override
    public String getPaymentStatus() {
        return PaymentStatus.CANCELLED;
    }
}
