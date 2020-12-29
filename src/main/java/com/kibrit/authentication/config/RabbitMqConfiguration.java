package com.kibrit.authentication.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfiguration {

    @Bean
    Queue voipUserUpdateQueue() {
        return new Queue("voip.user.update", false);
    }

    @Bean
    Queue voipUserDeleteQueue() {
        return new Queue("voip.user.delete", false);
    }

    @Bean
    Queue omniQueue() {
        return new Queue("omni.user.update", false);
    }

    @Bean
    Queue faqQueue() {
        return new Queue("faq.user.update", false);
    }

    @Bean
    Queue crmQueue() {
        return new Queue("crm.user.update", false);
    }

    @Bean
    Queue taskQueue() {
        return new Queue("task.user.update", false);
    }


    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("topic-exchange");
    }


//    @Bean
//    FanoutExchange fanoutExchange() {
//        return new FanoutExchange("fanout-exchange");
//    }
//
//    @Bean
//    Binding voipBinding(Queue voipQueue, FanoutExchange exchange) {
//        return BindingBuilder.bind(voipQueue).to(exchange);
//    }
//
//    @Bean
//    Binding crmBinding(Queue crmQueue, FanoutExchange exchange) {
//        return BindingBuilder.bind(crmQueue).to(exchange);
//    }
//
//    @Bean
//    Binding faqBinding(Queue faqQueue, FanoutExchange exchange) {
//        return BindingBuilder.bind(faqQueue).to(exchange);
//    }



    @Bean
    Binding voipUpdateBinding(Queue voipUserUpdateQueue, TopicExchange exchange) {
        return BindingBuilder.bind(voipUserUpdateQueue).to(exchange).with("#.update");
    }

    @Bean
    Binding voipDeleteBinding(Queue voipUserDeleteQueue, TopicExchange exchange) {
        return BindingBuilder.bind(voipUserDeleteQueue).to(exchange).with("#.delete");
    }

//    @Bean
//    Binding omniBinding(Queue omniQueue, TopicExchange exchange) {
//        return BindingBuilder.bind(omniQueue).to(exchange).with("queue.omni");
//    }

    @Bean
    Binding omniBinding(Queue omniQueue, TopicExchange exchange) {
        return BindingBuilder.bind(omniQueue).to(exchange).with("#.update");
    }

    @Bean
    Binding faqBinding(Queue faqQueue, TopicExchange exchange) {
        return BindingBuilder.bind(faqQueue).to(exchange).with("#.update");
    }


    @Bean
    Binding crmBinding(Queue crmQueue, TopicExchange exchange) {
        return BindingBuilder.bind(crmQueue).to(exchange).with("#.update");
    }

//    @Bean
//    Binding allQueueBinding(Queue allQueue, TopicExchange topicExchange) {
//        return BindingBuilder.bind(allQueue).to(topicExchange).with("queue.*");
//    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }
}
