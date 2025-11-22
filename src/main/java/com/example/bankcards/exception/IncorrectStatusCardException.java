package com.example.bankcards.exception;

public class IncorrectStatusCardException extends CardException {
    public IncorrectStatusCardException() {
        super("Некорректный статус карты. Возможны следующие варианты: ACTIVE, BLOCKED, EXPIRED!");
    }
}