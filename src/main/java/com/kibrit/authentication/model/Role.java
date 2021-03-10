package com.kibrit.authentication.model;

import com.fasterxml.jackson.annotation.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "roles")
@Data
public class Role implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Role name is required")
    private String name;

    @JoinColumn(name = "created_by", referencedColumnName = "id")
    @ManyToOne
    private User createdBy;

    @CreationTimestamp
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;
//
//    @JsonIgnore
//    @UpdateTimestamp
//    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
//    private LocalDateTime lastModificationDate;

//    @JsonIgnore
//    @JoinColumn(name = "last_modified_by", referencedColumnName = "id")
//    @ManyToOne
//    private User lastModifiedBy;

//    @ToString.Exclude
//    @ManyToMany(mappedBy="roles",cascade = {CascadeType.MERGE, CascadeType.PERSIST})
//    private List<User> users = new ArrayList<>();

    @ToString.Exclude
    @ManyToMany
    @JoinTable(name = "users_and_roles", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "roles_and_permissions", joinColumns = @JoinColumn(name = "role_id"), inverseJoinColumns = @JoinColumn(name = "permission_id"))
    private List<Permission> permissions = new ArrayList<>();
}