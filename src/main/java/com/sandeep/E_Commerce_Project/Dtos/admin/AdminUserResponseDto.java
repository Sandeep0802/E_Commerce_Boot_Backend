package com.sandeep.E_Commerce_Project.Dtos.admin;

import com.sandeep.E_Commerce_Project.Models.Role;
import lombok.Data;

import java.time.Instant;
import java.util.Set;

@Data
public class AdminUserResponseDto {
    private String id;
    private String username;
    private String email;
    private String role;
    private boolean enabled;
    private Instant createdAt;
    private long totalOrders;
}