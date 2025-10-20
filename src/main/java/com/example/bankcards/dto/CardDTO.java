package com.example.bankcards.dto;

import jakarta.validation.constraints.NotNull;

import java.time.Year;

public class CardDTO {
    private Integer id;

    @NotNull
    private String number;

    @NotNull
    private Integer user_id;

    @NotNull
    private Byte expMonth;

    @NotNull
    private Year expYear;

    @NotNull
    private String status;

    @NotNull
    private Float balance;
}
