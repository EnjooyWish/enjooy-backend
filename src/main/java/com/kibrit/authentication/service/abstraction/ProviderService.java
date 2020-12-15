package com.kibrit.authentication.service.abstraction;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kibrit.authentication.model.User;

public interface ProviderService {
    void sendUpdateInfo(User user);
}
