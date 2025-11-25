package com.example.bankcards.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CardDto {
    private Integer id;

    private String number;

    @NotNull
    private Integer userId;

    @FutureOrPresent
    private LocalDateTime expDate;

    @NotNull
    private String status;

    @NotNull
    @Digits(integer = 12, fraction = 2)
    private Float balance;
}