package com.example.Electrical.store.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;


@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> exceptionHandler(MethodArgumentNotValidException exception, WebRequest request) {

        Map<String, String> res = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach((error) -> {
            res.put(((FieldError) error).getField(), error.getDefaultMessage());
        });

        return new ResponseEntity<>(res, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(UserException.class)
    public ResponseEntity<MyErrorDetails> exceptionHandler(UserException exception, WebRequest request) {

        MyErrorDetails myErrorDetails = new MyErrorDetails(exception.getMessage(), request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(CurrentUserServiceException.class)
    public ResponseEntity<MyErrorDetails> exceptionHandler(CurrentUserServiceException exception, WebRequest request) {

        MyErrorDetails myErrorDetails = new MyErrorDetails(exception.getMessage(), request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(CategoryException.class)
    public ResponseEntity<MyErrorDetails> exceptionHandler(CategoryException exception, WebRequest request) {

        MyErrorDetails myErrorDetails = new MyErrorDetails(exception.getMessage(), request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(ProductException.class)
    public ResponseEntity<MyErrorDetails> exceptionHandler(ProductException exception, WebRequest request) {

        MyErrorDetails myErrorDetails = new MyErrorDetails(exception.getMessage(), request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<MyErrorDetails> exceptionHandler(Exception exception, WebRequest request) {
        MyErrorDetails myErrorDetails = new MyErrorDetails(exception.getMessage(), request.getDescription(false),
                LocalDateTime.now());

        return new ResponseEntity<>(myErrorDetails, HttpStatus.BAD_REQUEST);

    }

}
