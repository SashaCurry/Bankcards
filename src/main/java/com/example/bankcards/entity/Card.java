package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.Year;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private int id;

    @Column(name = "number")
    @NotNull
    private int number;

    @Column(name = "exp_month")
    @NotNull
    @Min(value = 1)
    @Max(value = 12)
    private Byte expMonth;

    @Column(name = "exp_year")
    @NotNull
    @FutureOrPresent
    private Year expYear;

    @Column(name = "status")
    @NotNull
    private String status;

    @Column(name = "balance")
    private Float balance;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User userId;

    public Card() {
    }
}