package com.kibrit.authentication.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kibrit.authentication.config.RabbitMqConfiguration;
import com.kibrit.authentication.model.LightUser;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.service.abstraction.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProviderServiceImpl implements ProviderService {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendUpdateInfo(User user)  {
        try {
            LightUser lightUser = new LightUser(user.getId(),user.getFullName());
            String userJson = new ObjectMapper().writeValueAsString(lightUser);
            logger.info("userJson = " + userJson);
            rabbitTemplate.convertAndSend(RabbitMqConfiguration.queueName, userJson);
            logger.info("Send successfully user = " + userJson);
        }catch (Exception e){
            logger.error("Exception happened while sending event from authentication " + e);
        }

    }
}
