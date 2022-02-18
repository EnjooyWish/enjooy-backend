package com.kibrit.authentication.config.amqp;

import com.kibrit.authentication.config.amqp.AmqpQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class OmniQueueConfig {
    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue omniQueue() {
        return QueueBuilder.durable(queueProperties.getOmniUserUpdate())
                .build();
    }

    @Bean
    Binding omniBinding(Queue omniQueue, TopicExchange exchange) {
        return BindingBuilder.bind(omniQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }
}
