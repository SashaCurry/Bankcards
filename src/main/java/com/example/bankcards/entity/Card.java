package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Lazy;

import java.math.BigDecimal;
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
    private BigDecimal balance;

    @NotNull
    @Column(name = "user_id")
    private Integer userId;

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