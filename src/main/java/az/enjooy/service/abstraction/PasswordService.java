package az.enjooy.service.abstraction;


import az.enjooy.dto.ResponseDTO;
import az.enjooy.dto.authentication.UserPasswordDTO;

public interface PasswordService {
    ResponseDTO resetPassword(String email, String password);
    boolean isValidPassword(String password, String encryptedPassword);
    ResponseDTO changePasswordByUser(UserPasswordDTO passwordDto);
}
