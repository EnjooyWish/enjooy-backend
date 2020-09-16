package com.kibrit.authentication.dto;

import com.kibrit.authentication.validation.PasswordMatch;
import com.kibrit.authentication.validation.UsernameExists;
import com.kibrit.authentication.validation.ValidEmail;
import com.kibrit.authentication.validation.ValidPassword;
import lombok.Data;
import javax.validation.constraints.NotBlank;

import java.io.Serializable;

@Data
@PasswordMatch(password = "password", confirmationPassword = "confirmationPassword")
public class UserDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Username is mandatory")
    @UsernameExists
    private String username;

    @ValidPassword
    private String password;

    private String confirmationPassword;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    private String middleName;

    @ValidEmail
    private String email;

    private String photo;
}
