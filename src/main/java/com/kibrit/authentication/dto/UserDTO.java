package com.kibrit.authentication.dto;

import com.kibrit.authentication.model.Role;
import com.kibrit.authentication.validation.ValidEmail;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class UserDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "User Number is mandatory")
    private String userNo;

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

    private List<RoleDTO> roles = new ArrayList<>();

    private boolean active = true;

    @Transient
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private String fullName;

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

}
