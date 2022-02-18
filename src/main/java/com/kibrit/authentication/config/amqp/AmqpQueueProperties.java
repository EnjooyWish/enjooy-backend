package com.kibrit.authentication.config.amqp;


import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("amqp.queue")
@Data
public class AmqpQueueProperties {
    private String voipUserUpdate;
    private String voipUserDelete;
    private String omniUserUpdate;
    private String faqUserUpdate;
    private String crmUserUpdate;
    private String smsUserUpdate;
    private String taskUserUpdate;
    private String reportUserUpdate;
    private String testUserUpdate;
    private String topicExchangeName;
    private String routingKeyUpdate;
    private String routingKeyDelete;
}
