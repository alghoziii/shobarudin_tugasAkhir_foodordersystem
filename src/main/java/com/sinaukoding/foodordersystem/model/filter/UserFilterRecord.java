package com.sinaukoding.foodordersystem.model.filter;

import com.sinaukoding.foodordersystem.model.enums.Role;
import com.sinaukoding.foodordersystem.model.enums.Status;

public record UserFilterRecord(String nama,
                               String email,
                               String username,
                               Status status,
                               Role role) {
}