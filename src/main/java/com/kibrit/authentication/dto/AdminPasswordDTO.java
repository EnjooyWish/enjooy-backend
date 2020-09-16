package com.kibrit.authentication.dto;


import com.kibrit.authentication.validation.PasswordMatch;
import com.kibrit.authentication.validation.ValidPassword;
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
