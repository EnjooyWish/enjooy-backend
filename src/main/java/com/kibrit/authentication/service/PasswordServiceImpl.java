package com.kibrit.authentication.service;

import com.kibrit.authentication.dto.UserPasswordDTO;
import com.kibrit.authentication.exception.InvalidOldPasswordException;
import com.kibrit.authentication.model.User;
import com.kibrit.authentication.repository.UserRepository;
import com.kibrit.authentication.service.abstraction.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    @Value("${default.password}")
    private String defaultPassword;

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder encoder;

    private final UserService userService;

    @Override
    public void resetPassword(Long id){
        User user = userService.findById(id);
        setDefaultPassword(user);
        userRepository.save(user);
    }

    public void setDefaultPassword(User user){
        user.setPassword(encoder.encode(defaultPassword));
    }

    @Override
    public boolean isValidPassword(String password, String encryptedPassword){
        return BCrypt.checkpw(password,encryptedPassword);
    }

    @Override
    public void changePasswordByUser(User user, UserPasswordDTO passwordDto){
        if (!isValidPassword(passwordDto.getOldPassword(),user.getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password");
        }else {
            user.setPassword(new BCryptPasswordEncoder().encode(passwordDto.getNewPassword()));
            userRepository.save(user);
        }
    }
}
