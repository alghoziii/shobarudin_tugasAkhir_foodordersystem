package com.sinaukoding.foodordersystem.model.request;


import com.sinaukoding.foodordersystem.model.enums.Role;
import com.sinaukoding.foodordersystem.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;



public record RegisterRequestRecord(
        @NotBlank(message = "Nama tidak boleh kosong")
        String nama,

        @NotBlank(message = "Username tidak boleh kosong")
        String username,

        @NotBlank(message = "Password tidak boleh kosong")
        @Size(min = 8, message = "Password minimal 8 karakter")
        String password,

        @NotBlank(message = "Email tidak boleh kosong")
        @Email(message = "Format email tidak valid")
        String email

) {}