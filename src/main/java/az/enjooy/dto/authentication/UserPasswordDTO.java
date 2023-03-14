package az.enjooy.dto.authentication;

import lombok.Data;

@Data
public class UserPasswordDTO {

    String username;
    String oldPassword;
    String newPassword;
    String newPasswordConfirmed;
}