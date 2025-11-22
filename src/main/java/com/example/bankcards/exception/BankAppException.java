package com.example.bankcards.exception;

public class BankAppException extends RuntimeException {
    public BankAppException(String message) {
        super(message);
    }
}