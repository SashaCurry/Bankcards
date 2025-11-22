package com.example.bankcards.entity;

import lombok.Getter;

@Getter
public enum StatusCard {
    ACTIVE("Активирована"),
    BLOCKED("Заблокирована"),
    EXPIRED("Истёк срок годности");

    private final String statusName;

    StatusCard(String statusName) {
        this.statusName = statusName;
    }
}