package com.sinaukoding.foodordersystem.model.request;

import com.sinaukoding.foodordersystem.model.enums.Role;
import com.sinaukoding.foodordersystem.model.enums.Status;

public record UserRequestRecord(String id,
                                String nama,
                                String username,
                                String email,
                                String password,
                                Status status,
                                Role role) {
}