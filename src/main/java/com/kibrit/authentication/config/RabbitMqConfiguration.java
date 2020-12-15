package com.kibrit.authentication.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {
   public static final String queueName = "userUpdate";

    @Bean
    Queue queue() {
        return new Queue(queueName, true);
    }

}
