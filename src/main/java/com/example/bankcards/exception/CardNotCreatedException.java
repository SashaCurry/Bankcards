package com.example.bankcards.exception;

public class CardNotCreatedException extends RuntimeException {
    public CardNotCreatedException(String message) {
        super(message);
    }
}
