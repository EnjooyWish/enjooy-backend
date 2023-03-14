package az.enjooy.dto;


import az.enjooy.validation.PasswordMatch;
import az.enjooy.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@PasswordMatch(password = "newPassword", confirmationPassword = "confirmationPassword")
public class AdminPasswordDTO {
    @NotNull
    @ValidPassword
    private String newPassword;

    @NotNull
    private String confirmationPassword;
}
