package com.sinaukoding.foodordersystem.model.filter;

import com.sinaukoding.foodordersystem.model.enums.OrderStatus;

public record OrderFilterRecord(
        String userId,
        OrderStatus status,
        String tanggalMulai,
        String tanggalSelesai
) {

}
