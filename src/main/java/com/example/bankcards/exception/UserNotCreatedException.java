package com.example.bankcards.exception;

public class UserNotCreatedException extends UserException {
    public UserNotCreatedException(String message) {
        super("Пользователь с login = " + message + " уже существует!");
    }
}