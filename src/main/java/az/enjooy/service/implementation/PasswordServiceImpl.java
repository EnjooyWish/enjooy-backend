package az.enjooy.service.implementation;

import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.authentication.UserPasswordDTO;
import az.enjooy.exception.InvalidOldPasswordException;
import az.enjooy.model.entity.User;
import az.enjooy.repository.UserRepository;
import az.enjooy.service.abstraction.PasswordService;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PasswordServiceImpl implements PasswordService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder encoder;

    @Override
    public ResponseDTO resetPassword(String email, String password) {
        User user = userRepository.findByEmail(email).orElse(null);
        if(user==null) throw new IllegalStateException("User not found");
        user.setPassword(encoder.encode(password));
        userRepository.save(user);
        return new ResponseDTO();
    }

    @Override
    public boolean isValidPassword(String password, String encryptedPassword){
        return BCrypt.checkpw(password,encryptedPassword);
    }

    @Override
    public ResponseDTO changePasswordByUser(UserPasswordDTO passwordDto){
        User authenticatedUser = userRepository.findByUsername(passwordDto.getUsername());
        if (!isValidPassword(passwordDto.getOldPassword(),authenticatedUser.getPassword())) {
            throw new InvalidOldPasswordException("Invalid old password");
        }else {
            authenticatedUser.setPassword(new BCryptPasswordEncoder().encode(passwordDto.getNewPassword()));
            userRepository.save(authenticatedUser);
        }
        return new ResponseDTO();
    }
}
