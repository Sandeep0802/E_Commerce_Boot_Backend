package com.sandeep.E_Commerce_Project.Dtos.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    private String password;
}
