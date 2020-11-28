package com.kibrit.authentication.exception;

import com.kibrit.authentication.util.GenericResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        logger.error("Validation errors", ex);
        List<String>  errors = new ArrayList<>();
        List<String>  fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + " : " + error.getDefaultMessage())
                .collect(Collectors.toList());
        List<String>  globalErrors = ex.getBindingResult()
                .getGlobalErrors()
                .stream()
                .map(error -> error.getObjectName() + " : " +error.getDefaultMessage() )
                .collect(Collectors.toList());
        errors.addAll(fieldErrors);
        errors.addAll(globalErrors);
        GenericResponse genericResponse = new GenericResponse("Validation errors " , errors);
        return new ResponseEntity<>(genericResponse, headers, status);
    }

    @ExceptionHandler({ ConstraintViolationException.class })
    public ResponseEntity<Object> handleConstraintViolation(
            ConstraintViolationException ex, WebRequest request) {
        logger.error("Constraint Violations", ex);
        List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getRootBeanClass().getName() + " " +
                    violation.getPropertyPath() + " : " + violation.getMessage());
        }
        GenericResponse apiError = new GenericResponse("Constraint Violations", ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<Object> handleResourceNotFoundException(
            final RuntimeException ex, final WebRequest request) {
        logger.error("Resource Not Found", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Resource Not Found", ex.getMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternal(
            final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Server error", ex.getLocalizedMessage());
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidOldPasswordException.class})
    public ResponseEntity<Object> handleInvalidOldPassword(
            final RuntimeException ex, final WebRequest request) {
        logger.error("Invalid old password", ex);
        final GenericResponse bodyOfResponse =
                new GenericResponse("Invalid old password", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({UsernameAlreadyExistsException.class})
    public ResponseEntity<Object> handleUsernameAlreadyExistsException(
            final RuntimeException ex, final WebRequest request) {
        logger.error("User already exists", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("User already exists", ex.getMessage());
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Object> handleMethodArgumentTypeMismatch(
            MethodArgumentTypeMismatchException ex,
            WebRequest request) {
        logger.error("Mismatch Type", ex);
        final GenericResponse apiError = new GenericResponse("Mismatch Type", ex.getMessage());
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(
            NoHandlerFoundException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        logger.error("Method Not Found", ex);
        final GenericResponse apiError = new GenericResponse("Method Not Found",
                String.format("Could not find the %s method for URL %s", ex.getHttpMethod(), ex.getRequestURL()));
        return new ResponseEntity<>(apiError, new HttpHeaders(), HttpStatus.NOT_FOUND);

    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {
        logger.error("Missing Parameters", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Missing Parameters",  "Parameter " +  "'" +ex.getParameterName() + "'" + " is missing");
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

}
