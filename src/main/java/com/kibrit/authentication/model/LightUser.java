package com.kibrit.authentication.model;

import lombok.Data;

@Data
public class LightUser {
    private Long id;
    private String firstName;
    private String lastName;

    public LightUser(User user) {
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
    }
}