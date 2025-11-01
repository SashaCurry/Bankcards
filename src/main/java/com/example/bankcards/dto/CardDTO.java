package com.example.bankcards.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.Year;

public class CardDTO {
    private Integer id;

    @NotNull
    private String number;

    @NotNull
    private Integer userId;

    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private Byte expMonth;

    @NotNull
    @FutureOrPresent
    private Year expYear;

    @NotNull
    private String status;

    @NotNull
    private Float balance;
}
