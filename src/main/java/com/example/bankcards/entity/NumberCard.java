package com.example.bankcards.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class NumberCard {
    private int section1;
    private int section2;
    private int section3;
    private int section4;


    public NumberCard(String number) {
        setNumberCard(number);
    }


    public String getNumberCard() {
        return section1 + " " + section2 + " " + section3 + " " + section4;
    }


    public void setNumberCard(String newNumber) {
        String[] sections = newNumber.split(" ");
        this.section1 = Integer.parseInt(sections[0]);
        this.section2 = Integer.parseInt(sections[1]);
        this.section3 = Integer.parseInt(sections[2]);
        this.section4 = Integer.parseInt(sections[3]);
    }


    @Override
    public String toString() {
        return section1 + " " + section2 + " " + section3 + " " + section4;
    }
}
