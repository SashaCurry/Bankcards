package com.example.bankcards.exception;

public class IncorrectStatusExpection extends RuntimeException {
    public IncorrectStatusExpection(String message) {
        super(message);
    }
}
