package com.kibrit.authentication.config.amqp;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class SmsQueueConfig {
    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue smsQueue() {
        return  new Queue(queueProperties.getSmsUserUpdate(), true);
    }

    @Bean
    Binding smsBinding(Queue smsQueue, TopicExchange exchange) {
        return BindingBuilder.bind(smsQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }
}
