package com.sinaukoding.foodordersystem.model.request;

import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderUpdateRequestRecord(
        @NotBlank(message = "OrderId tidak boleh kosong")
        String id,

        @NotNull(message = "Status tidak boleh kosong")
        OrderStatus status
) {
}
