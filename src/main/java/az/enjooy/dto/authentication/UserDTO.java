package az.enjooy.dto.authentication;

import az.enjooy.dto.RoleDTO;
import az.enjooy.validation.ValidEmail;
import lombok.AccessLevel;
import lombok.Getter;

import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class UserDTO  implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String photo;

    @Pattern(regexp = "^((?![0-9.])[a-zA-Z0-9.]{5,30}+(?<![_.]))$",message = "Username must be at least 5 characters long." +
            "Only alphanumeric characters and period is allowed.")
    private String username;

    private String firstName;

    private String lastName;

    @ValidEmail
    private String email;

    private String password;

    private String confirmationPassword;

    private String phoneNumber;

    private String gender;

    private List<RoleDTO> roles = new ArrayList<>();

    private boolean active = true;

    @Transient
    @Getter(AccessLevel.NONE)
    private String fullName;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getGender() {
        return gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmationPassword() {
        return confirmationPassword;
    }

    public void setConfirmationPassword(String confirmationPassword) {
        this.confirmationPassword = confirmationPassword;
    }

    public List<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDTO> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
