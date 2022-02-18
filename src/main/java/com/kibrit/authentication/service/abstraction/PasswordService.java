package com.kibrit.authentication.service.abstraction;


import com.kibrit.authentication.dto.UserPasswordDTO;
import com.kibrit.authentication.model.User;

public interface PasswordService {
    void resetPassword(Long id);
    boolean isValidPassword(String password, String encryptedPassword);
    void changePasswordByUser(User user, UserPasswordDTO passwordDto);
}
