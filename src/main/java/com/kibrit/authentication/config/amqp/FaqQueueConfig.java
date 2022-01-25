package com.kibrit.authentication.config.amqp;

import com.kibrit.authentication.config.amqp.AmqpQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class FaqQueueConfig {

    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue faqQueue() {
        return QueueBuilder.durable(queueProperties.getFaqUserUpdate())
                .build();
    }

    @Bean
    Binding faqBinding(Queue faqQueue, TopicExchange exchange) {
        return BindingBuilder.bind(faqQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }

}
