package com.kibrit.authentication.dto;

import com.kibrit.authentication.validation.ValidEmail;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class UserUpdateDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String photo;

    @NotBlank(message = "First Name is mandatory")
    private String firstName;

    @NotBlank(message = "Last Name is mandatory")
    private String lastName;

    private String middleName;

    @ValidEmail
    private String email;
}
