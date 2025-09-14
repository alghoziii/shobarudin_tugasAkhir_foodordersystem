package com.sinaukoding.foodordersystem.model.enums;

import lombok.Getter;

@Getter
public enum OrderStatus {
    DRAFT("Draft"),
    CONFIRMED("Dikonfirmasi"),
    PAID("Sudah Dibayar"),
    CANCELLED("Dibatalkan");

    private final String label;

    OrderStatus(String label) {
        this.label = label;
    }
}
