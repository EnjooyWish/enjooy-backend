package az.enjooy.model.entity;

import com.fasterxml.jackson.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "users")
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

    private String photoUrl;

    private String phoneNumber;

    private String gender;

    private LocalDateTime createdAt;

    @JsonIgnore
    @ManyToMany(mappedBy = "users", fetch = FetchType.EAGER)
    @OrderBy("id")
    private Set<Role> roles = new LinkedHashSet();

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
    private boolean active;

    @Transient
//    @Getter(AccessLevel.NONE)
    private String fullName;

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getFullName() {
        if (lastName == null)
            return firstName;
        return firstName + " " + lastName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
