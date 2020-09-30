package com.kibrit.authentication.validation;

import com.kibrit.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

//@Service
public class UsernameValidator implements ConstraintValidator<UsernameExists, String> {

    @Autowired
    private UserService userService;

    @Override
    public void initialize(UsernameExists constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.userExists(s);
    }
}