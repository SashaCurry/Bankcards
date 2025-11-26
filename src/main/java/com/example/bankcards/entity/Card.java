package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "cards")
@AllArgsConstructor
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_card")
    private Integer id;

    @Embedded
    private NumberCard number;

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

    public String getNumber() {
        return number == null ? null : number.getNumber();
    }

    public void setNumber(String newNumber) {
        if (this.number == null) {
            this.number = new NumberCard();
        }
        this.number.setNumber(newNumber);
    }
}