package com.kibrit.authentication.config.amqp;

import com.kibrit.authentication.config.amqp.AmqpQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class CrmQueueConfig {
    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue crmQueue() {
        return QueueBuilder.durable(queueProperties.getCrmUserUpdate())
                .build();
    }

    @Bean
    Binding crmBinding(Queue crmQueue, TopicExchange exchange) {
        return BindingBuilder.bind(crmQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }
}
