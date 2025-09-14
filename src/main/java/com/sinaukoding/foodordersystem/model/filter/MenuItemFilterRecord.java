package com.sinaukoding.foodordersystem.model.filter;

import com.sinaukoding.foodordersystem.model.enums.Status;

public record MenuItemFilterRecord(
        String nama,
        Status status,
        Integer stok,
        Double hargaMin,
        Double hargaMax
) {
}