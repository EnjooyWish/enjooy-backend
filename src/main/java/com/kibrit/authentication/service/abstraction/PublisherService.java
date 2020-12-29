package com.kibrit.authentication.service.abstraction;

import com.kibrit.authentication.model.User;

public interface PublisherService {
    void publishUserEvent(User user, String routingKey);
}
