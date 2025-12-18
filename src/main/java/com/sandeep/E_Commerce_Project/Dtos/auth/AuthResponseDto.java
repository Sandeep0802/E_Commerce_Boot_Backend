package com.sandeep.E_Commerce_Project.Dtos.auth;

import lombok.Data;

import java.util.Set;

@Data
public class AuthResponseDto {
    private String token;
    private String tokenType = "Bearer";

    private Set<String> roles;

    public AuthResponseDto(String token, Set<String> roles) {
        this.token = token;
        this.roles = roles;
    }
}