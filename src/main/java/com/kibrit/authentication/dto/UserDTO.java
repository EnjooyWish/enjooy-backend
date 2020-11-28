package com.kibrit.authentication.dto;

import com.kibrit.authentication.validation.ValidEmail;
import lombok.Data;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;

@Data
public class UserDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String photo;

    @Pattern(regexp = "^((?![0-9.])[a-zA-Z0-9.]{5,30}+(?<![_.]))$",message = "Username must be at least 5 characters long." +
            "Only alphanumeric characters and period is allowed.")
    private String username;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    @ValidEmail
    private String email;

}
