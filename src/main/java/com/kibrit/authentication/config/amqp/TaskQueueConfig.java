package com.kibrit.authentication.config.amqp;

import com.kibrit.authentication.config.amqp.AmqpQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class TaskQueueConfig {

    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue taskQueue() {
        return QueueBuilder.durable(queueProperties.getTaskUserUpdate())
                .build();
    }

    @Bean
    Binding taskBinding(Queue taskQueue, TopicExchange exchange) {
        return BindingBuilder.bind(taskQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }

}
