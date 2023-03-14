package az.enjooy.dto.authentication;

import lombok.Data;

@Data
public class PasswordResetDTO {
    String email;
    String password;
}
