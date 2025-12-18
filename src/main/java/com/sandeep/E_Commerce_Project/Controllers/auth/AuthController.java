package com.sandeep.E_Commerce_Project.Controllers.auth;

import com.sandeep.E_Commerce_Project.Dtos.auth.AuthRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.auth.AuthResponseDto;
import com.sandeep.E_Commerce_Project.Dtos.auth.RegisterRequestDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/auth")
public class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> register(
            @Valid @RequestBody RegisterRequestDto request,
            HttpServletRequest req
    )
    {
        auth.register(request);

        return ResponseEntity.status(201)
                .body(ApiResponse.success(
                        "User registered successfully",
                        null,
                        req.getRequestURI(),
                        201
                ));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<?>> login(
            @Valid @RequestBody AuthRequestDto request,
            HttpServletRequest req
    ) {
        AuthResponseDto loginData = auth.login(request);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Login successful",
                        loginData,
                        req.getRequestURI(),
                        200
                )
        );
    }
}

