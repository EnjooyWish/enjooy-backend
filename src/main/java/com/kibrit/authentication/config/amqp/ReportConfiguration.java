package com.kibrit.authentication.config.amqp;

import com.kibrit.authentication.config.amqp.AmqpQueueProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class ReportConfiguration {

    private final AmqpQueueProperties queueProperties;

    @Bean
    Queue reportQueue() {
        return QueueBuilder.durable(queueProperties.getReportUserUpdate())
                .build();
    }

    @Bean
    Binding reportBinding(Queue reportQueue, TopicExchange exchange) {
        return BindingBuilder.bind(reportQueue)
                .to(exchange)
                .with(queueProperties.getRoutingKeyUpdate());
    }

}
