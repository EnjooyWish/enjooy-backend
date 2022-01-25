package com.kibrit.authentication.config.amqp;

import com.kibrit.authentication.config.amqp.AmqpQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class VoipQueueConfig {

    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue voipUserUpdateQueue() {
        return QueueBuilder.durable(queueProperties.getVoipUserUpdate())
                .build();
    }

    @Bean
    Queue voipUserDeleteQueue() {
        return QueueBuilder.durable(queueProperties.getVoipUserDelete())
                .build();
    }

    @Bean
    Binding voipUpdateBinding(Queue voipUserUpdateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(voipUserUpdateQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }

    @Bean
    Binding voipDeleteBinding(Queue voipUserDeleteQueue, TopicExchange exchange) {
        return BindingBuilder.bind(voipUserDeleteQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyDelete());
    }
}
