package com.example.bankcards.exception;

public class UserNotFoundException extends UserException {
    public UserNotFoundException(String message) {
        super("Пользователь с id = " + message + " не найден!");
    }
}
