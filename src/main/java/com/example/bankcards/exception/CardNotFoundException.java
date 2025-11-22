package com.example.bankcards.exception;

public class CardNotFoundException extends CardException {
    public CardNotFoundException(String message) {
        super("Карта с id = " + message + " не найдена!");
    }
}
