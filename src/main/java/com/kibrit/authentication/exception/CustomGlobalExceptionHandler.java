package com.kibrit.authentication.exception;

import com.kibrit.authentication.util.GenericResponse;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.*;

@ControllerAdvice
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("message", ex.getLocalizedMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult()
                .getFieldErrors()
                .forEach(fieldError -> errors.put(fieldError.getField(),fieldError.getDefaultMessage()));
        ex.getBindingResult()
                .getGlobalErrors()
                .forEach(error -> errors.put(error.getObjectName(), error.getDefaultMessage()));
        body.put("error", errors);
        body.put("response", null);
        return new ResponseEntity<>(body, headers, status);
//        final List<String> errors = new ArrayList<>();
//        ex.getBindingResult()
//                .getFieldErrors()
//                .forEach(fieldError -> errors.add(fieldError.getField() + ": " + fieldError.getDefaultMessage()));
//

//        final GenericResponse body = new GenericResponse(ex.getLocalizedMessage(), errors);
//        final GenericResponse body = new GenericResponse(ex.getBindingResult().getAllErrors(), "InvalidMethodArgument-" + ex.getBindingResult().getObjectName());
//        return new ResponseEntity<>(body, headers, status);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternal(
            final RuntimeException ex, final WebRequest request) {
        logger.error("500 Status Code", ex);
        final GenericResponse bodyOfResponse = new GenericResponse("Internal Server Error", "InternalError");
        return new ResponseEntity<>(bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({InvalidOldPasswordException.class})
    public ResponseEntity<Object> handleInvalidOldPassword(
            final RuntimeException ex, final WebRequest request) {
        final GenericResponse bodyOfResponse =
                new GenericResponse("Invalid old password");
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

}
