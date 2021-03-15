package com.kibrit.authentication.model;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
@Getter
@Setter
@ApiModel("AcceptedMessage")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @ApiModelProperty(notes = "Auto generated user id", example = "1")
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
    @Basic(fetch = FetchType.LAZY)
    @ApiModelProperty(notes = "User Profile Picture", example = "Base64")
    private String photo;

    @JsonIgnore
    @ManyToMany(mappedBy="users",fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Role> roles =new LinkedHashSet();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Basic(optional = false)
    @Column(name = "is_active")
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

    public void addRole(Role role) {
        this.roles.add(role);
        role.getUsers().add(this);
    }

    public void removeRole(Role role) {
        this.roles.remove(role);
        role.getUsers().remove(this);
    }
}
