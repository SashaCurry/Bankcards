package com.example.bankcards.controller;

import com.example.bankcards.exception.*;
import com.example.bankcards.exception.ErrorResponse;
import jakarta.validation.ConstraintViolationException;
import org.hibernate.HibernateException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler(HttpMessageNotReadableException.class)
//    private ResponseEntity<ErrorResponse> handleException(HttpMessageNotReadableException e) {
//        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//
//    @ExceptionHandler(IllegalArgumentException.class)
//    private ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
//        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }

    @ExceptionHandler(Exception.class)
    private ResponseEntity<ErrorResponse> handleException(Exception e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    //////////////////////////////////////ИСКЛЮЧЕНИЯ ДЛЯ ПОЛЬЗОВАТЕЛЕЙ//////////////////////////////////////////////////

    @ExceptionHandler(UserNotCreatedException.class)
    private ResponseEntity<ErrorResponse> handleException(UserNotCreatedException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(UserNotFoundException.class)
    private ResponseEntity<ErrorResponse> handleException(UserNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }


    // Не валидные данные на уровне DTO
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleExceptions(MethodArgumentNotValidException e) {
        StringBuilder str = new StringBuilder();
        e.getBindingResult().getFieldErrors().forEach(error -> {
            String errorMessage = error.getDefaultMessage();
            str.append(errorMessage).append("! ");
        });

        ErrorResponse response = new ErrorResponse(String.valueOf(str), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }


    // Не валидные данные на уровне сущности
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleExceptions(ConstraintViolationException e) {
        StringBuilder str = new StringBuilder();
        e.getConstraintViolations().forEach(violation -> {
            String errorMessage = violation.getMessage();
            str.append(errorMessage).append("! ");
        });

        ErrorResponse response = new ErrorResponse(String.valueOf(str), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    ////////////////////////////////////////////ИСКЛЮЧЕНИЯ ДЛЯ КАРТ/////////////////////////////////////////////////////

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(CardNotFoundException e) {
        ErrorResponse response = new ErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}