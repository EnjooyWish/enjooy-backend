package com.kibrit.authentication.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.kibrit.authentication.validation.PasswordMatch;
import com.kibrit.authentication.validation.UsernameExists;
import com.kibrit.authentication.validation.ValidEmail;
import com.kibrit.authentication.validation.ValidPassword;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Data
@ApiModel("AcceptedMessage")
public class User  extends Audit<String> implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(notes = "Auto generated user id", example = "1")
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ApiModelProperty(notes = "Username must be unique", example = "samirs", required = true)
    private String username;

    @JsonIgnore
    @ApiModelProperty(notes = "Password must contain at least one uppercase, lowercase and digit and be at least 8 character length", example = "Az123456")
    private String password;

    @ApiModelProperty(notes = "User's Name", example = "Emil", required = true)
    private String firstName;

    @ApiModelProperty(notes = "User Last Name", example = "Aliyev", required = true)
    private String lastName;

    @ApiModelProperty(notes = "User Father Name", example = "Kərəm")
    private String middleName;

    @ApiModelProperty(notes = "User Email Address", example = "emil.aliyev@kibrit.tech")
    private String email;

    @Lob
    @ApiModelProperty(notes = "User Profile Picture", example = "Base64")
    private String photo;

    @JsonIgnore
    @JoinTable(name = "users_and_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    @ManyToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<Role> roles;

}
