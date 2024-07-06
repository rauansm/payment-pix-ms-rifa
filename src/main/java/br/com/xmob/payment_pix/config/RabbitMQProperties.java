package br.com.xmob.payment_pix.config;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter(value = AccessLevel.PACKAGE)
@Component
@ConfigurationProperties(prefix = "rabbitmq")
public class RabbitMQProperties {
    private String paymentStatusExchange;
    private String paymentAprovedQueue;
    private String paymentExpiredQueue;
    private String paymentExpiredRoutingKey;
    private String paymentAprovedRoutingKey;
}
