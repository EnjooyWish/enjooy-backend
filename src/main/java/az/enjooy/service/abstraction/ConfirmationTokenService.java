package az.enjooy.service.abstraction;

import az.enjooy.model.entity.ConfirmationToken;

public interface ConfirmationTokenService {
    ConfirmationToken findByToken(String token);
    void setConfirmedAt(String token);
    void saveConfirmationToken(ConfirmationToken token);
}
