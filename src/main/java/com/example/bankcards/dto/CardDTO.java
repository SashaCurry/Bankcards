package com.example.bankcards.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.Year;

@Data
public class CardDTO {
    private Integer id;

    private String number;

    @NotNull(message = "Id пользователя userId не может быть пустым")
    private Integer userId;

    @Min(value = 1, message = "Минимальное значение месяца: 1 (январь)")
    @Max(value = 12, message = "Максимальное значение месяца: 12 (декабрь)")
    private Byte expMonth;

    @FutureOrPresent(message = "Параметр expYear должен быть равен текущему году или будущему")
    private Year expYear;

    @NotNull(message = "Статус карты status не может быть пустым (Active, Blocked, Expired)")
    private String status;

    @NotNull(message = "Баланс карты balance не может быть пустым")
    private Float balance;
}