package com.sinaukoding.foodordersystem.model.request;

import com.sinaukoding.foodordersystem.model.enums.OrderStatus;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record OrderRequestRecord(

        OrderStatus status,

        @Valid
        @NotNull(message = "Item pesanan tidak boleh kosong")
        List<OrderItemRequestRecord> items
) {}