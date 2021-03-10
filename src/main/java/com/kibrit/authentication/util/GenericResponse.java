package com.kibrit.authentication.util;

import lombok.Getter;
import lombok.Setter;
import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class GenericResponse {
    private String message;
    private List<String> errors;
    private Object response;

    public GenericResponse() {
    }
    public GenericResponse(String message, List errors) {
        super();
        this.message = message;
        this.errors = errors;
    }

    public GenericResponse(String message, String error) {
        super();
        this.message = message;
        this.errors = Arrays.asList(error);
    }

}

