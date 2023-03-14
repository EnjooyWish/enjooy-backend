package az.enjooy.validation;

import az.enjooy.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<UsernameExists, String> {

    @Autowired
    private UserServiceImpl userService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return !userService.userExists(s);
    }
}