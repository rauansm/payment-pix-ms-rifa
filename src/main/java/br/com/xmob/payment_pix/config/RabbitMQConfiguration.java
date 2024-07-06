package br.com.xmob.payment_pix.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfiguration {
    private final ConnectionFactory connectionFactory;
    private final RabbitMQProperties properties;

    @Bean
    public Queue paymentAprovedQueue() {
        return QueueBuilder
                .durable(properties.getPaymentAprovedQueue())
                .build();
    }

    @Bean
    public Queue paymentExpiredQueue() {
        return QueueBuilder
                .durable(properties.getPaymentExpiredQueue())
                .build();
    }

    @Bean
    public RabbitAdmin createRabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public ApplicationListener<ApplicationReadyEvent> initializeAdmin (RabbitAdmin rabbitAdmin){
        return event -> rabbitAdmin.initialize();
    }

    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder
                .directExchange(properties.getPaymentStatusExchange())
                .build();
    }

    @Bean
    public Binding bindingPaymentAprovedQueue() {
        return BindingBuilder.bind(paymentAprovedQueue())
                .to(directExchange())
                .with(properties.getPaymentAprovedRoutingKey());
    }

    @Bean
    public Binding bindingPaymentExpiredQueue() {
        return BindingBuilder.bind(paymentExpiredQueue())
                .to(directExchange())
                .with(properties.getPaymentExpiredRoutingKey());
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter());
        return rabbitTemplate;
    }
}
