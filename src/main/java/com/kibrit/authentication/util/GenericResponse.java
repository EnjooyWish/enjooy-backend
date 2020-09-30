package com.kibrit.authentication.util;

import lombok.Data;

@Data
public class GenericResponse {
    private String message;
    private String error;
    private Object response;

    public GenericResponse(final String message) {
        super();
        this.message = message;
    }

    public GenericResponse(final String message, final String error) {
        super();
        this.message = message;
        this.error = error;
    }

    public GenericResponse(final String message, final String error, final Object response) {
        super();
        this.message = message;
        this.error = error;
        this.response = response;
    }

//    public GenericResponse(List<ObjectError> allErrors, String error) {
//        this.error = error;
//        String temp = allErrors.stream().map(e -> {
//            if (e instanceof FieldError) {
//                return "{\"field\":\"" + ((FieldError) e).getField() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            } else {
//                return "{\"object\":\"" + e.getObjectName() + "\",\"defaultMessage\":\"" + e.getDefaultMessage() + "\"}";
//            }
//        }).collect(Collectors.joining(","));
//        this.message = "[" + temp + "]";
//    }
}
