package com.kibrit.authentication.validation;




import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, Object> {

     private String password;
     private String confirmationPassword;
     private String message;

    @Override
    public void initialize(PasswordMatch constraintAnnotation) {
        password = constraintAnnotation.password();
        confirmationPassword = constraintAnnotation.confirmationPassword();
        message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(final Object value, final ConstraintValidatorContext context) {
        boolean isValidPassword = false;
        try
        {
            final Object  firstPassword = BeanUtils.getProperty(value, password);
            final Object  secondPassword = BeanUtils.getProperty(value, confirmationPassword);
            isValidPassword =  firstPassword == null && secondPassword == null || firstPassword != null && firstPassword.equals(secondPassword);
        }
        catch (final Exception ignore)
        {
            // ignore
        }

        if (!isValidPassword){
            context.buildConstraintViolationWithTemplate(message)
                    .addPropertyNode(password)
                    .addConstraintViolation()
                    .disableDefaultConstraintViolation();
        }
        return isValidPassword;
    }
}