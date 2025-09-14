package com.sinaukoding.foodordersystem.model.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OrderItemRequestRecord(
        @NotBlank(message = "Nama menu tidak boleh kosong")
        String menuName,
        @NotNull(message = "Qty tidak boleh kosong")
        @Min(value = 1, message = "Qty minimal 1")
        Integer qty
) {}