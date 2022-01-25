package com.kibrit.authentication.service;

import com.kibrit.authentication.model.LightUser;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.abstraction.PublisherService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PublisherServiceImpl implements PublisherService {
    Logger logger = LoggerFactory.getLogger(getClass());

    private final RabbitTemplate rabbitTemplate;

    private final TopicExchange topicExchange;

    @Override
    public void publishUserEvent(User user,String routingKey)  {
        try {
            LightUser lightUser = new LightUser(user);
            logger.info("" + lightUser);
            rabbitTemplate.convertAndSend(topicExchange.getName(),routingKey, lightUser);
            logger.info("Send successfully user = " + lightUser);
        }catch (Exception e){
            logger.error("Exception happened while sending event from authentication " + e);
        }

    }
}
