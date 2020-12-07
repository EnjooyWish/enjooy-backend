package com.kibrit.authentication.model;

import lombok.Data;

@Data
public class LightUser {
    private Long id;
    private String fullName;

    public LightUser(Long id, String fullName) {
        this.id = id;
        this.fullName = fullName;
    }
}
