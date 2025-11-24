package com.example.bankcards.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.Year;

@Data
public class CardDTO {
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