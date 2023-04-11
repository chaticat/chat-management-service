package com.chaticat.chatmanagementservice.config;

import com.chaticat.chatmanagementservice.exception.InvalidJwtAuthenticationException;
import com.chaticat.chatmanagementservice.exception.InvalidTokenException;
import com.chaticat.chatmanagementservice.exception.UserNotFoundException;
import lombok.extern.log4j.Log4j2;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@Log4j2
@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(value = {UserNotFoundException.class})
    public ResponseEntity<String> userNotFound(UserNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = {InvalidJwtAuthenticationException.class})
    public ResponseEntity<String> invalidJwtAuthentication(InvalidJwtAuthenticationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = {InvalidTokenException.class})
    public ResponseEntity<String> invalidTokenException(InvalidTokenException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<String> validationException(MethodArgumentNotValidException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ConstraintViolationException.class})
    public ResponseEntity<String> validationException(ConstraintViolationException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
