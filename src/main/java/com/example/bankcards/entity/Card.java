package com.example.bankcards.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
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
    private Integer id;

    @Column(name = "number")
    @NotNull(message = "Номер карты number не может быть пустым")
    @NotBlank(message = "Номер карты number не может состоять только из пробелов")
    private String number;

    @Column(name = "exp_month")
    @NotNull(message = "Параметр expMonth (месяц истечения срока годности) не может быть пустым")
    @Min(value = 1, message = "Минимальное значение месяца: 1 (январь)")
    @Max(value = 12, message = "Максимальное значение месяца: 12 (декабрь)")
    private Byte expMonth;

    @Column(name = "exp_year")
    @NotNull(message = "Параметр expYear (год истечения срока годности) не может быть пустым")
    @FutureOrPresent(message = "Параметр expYear должен быть равен текущему году или будущему")
    private Year expYear;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Статус карты (Активна, Заблокирована, Истёк срок) не может быть пустым")
    private StatusCard status;

    @Column(name = "balance")
    @NotNull(message = "Баланс карты balance не может быть пустым")
    private Float balance;

    @NotNull(message = "Id пользователя userId не может быть пустым")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id_user")
    private User userId;

    public Card() {
    }
}