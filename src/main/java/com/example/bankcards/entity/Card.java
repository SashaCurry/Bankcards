package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private Integer id;

    @Column(name = "number")
    @NotBlank
    private String number;

    @Column(name = "exp_date")
    @FutureOrPresent
    private LocalDateTime expDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull
    private StatusCard status;

    @Column(name = "balance")
    @NotNull
    private Float balance;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User userId;

    public Card() {
    }
}