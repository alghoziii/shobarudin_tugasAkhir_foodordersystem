package com.sinaukoding.foodordersystem.model.request;

import com.sinaukoding.foodordersystem.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;

import java.util.Set;

public record MenuItemRequestRecord( @Schema(hidden = true) String id,
                                    @NotBlank(message = "Nama tidak boleh kosong") String nama,
                                    @NotBlank(message = "deskripsi tidak boleh kosong") String deskripsi,
                                    @NotNull(message = "Harga tidak boleh kosong") Double harga,
                                    @NotNull(message = "Stok tidak boleh kosong") Integer stok,
                                    @NotNull(message = "Status tidak boleh kosong") Status status,
                                    @NotEmpty(message = "Gambar tidak boleh kosong") Set<String> listImage) {
}