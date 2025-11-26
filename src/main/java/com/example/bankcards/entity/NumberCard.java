package com.example.bankcards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Embeddable
@NoArgsConstructor
@Getter
public class NumberCard {
    @Column(name = "number")
    private String number;


    public NumberCard(String number) {
        setNumber(number);
    }


    public void setNumber(String number) {
//        String[] sections = number.split(" ");
//        this.number = Integer.parseInt(sections[0]) + " "
//                + Integer.parseInt(sections[1]) + " "
//                + Integer.parseInt(sections[2]) + " "
//                + Integer.parseInt(sections[3]);
        this.number = number;
    }


    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        NumberCard that = (NumberCard) o;
        return Objects.equals(number, that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(number);
    }

    @Override
    public String toString() {
        return number;
    }
}