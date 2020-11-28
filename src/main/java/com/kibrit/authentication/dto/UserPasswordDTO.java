package com.kibrit.authentication.dto;

import com.kibrit.authentication.validation.PasswordMatch;
import com.kibrit.authentication.validation.ValidPassword;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@PasswordMatch(password = "newPassword", confirmationPassword = "confirmationPassword")
public class UserPasswordDTO {

    private String oldPassword;

    @NotBlank
    @ValidPassword
    private String newPassword;

    @NotBlank
    private String confirmationPassword;
}