package com.sandeep.E_Commerce_Project.Controllers.checkout;


import com.sandeep.E_Commerce_Project.Dtos.checkout.CheckoutRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentResponseDto;
import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentVerifyDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Models.Order;

import com.sandeep.E_Commerce_Project.Services.checkout.CheckoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    // 🔹 CREATE ORDER + PAYMENT
    @PostMapping
    public ResponseEntity<ApiResponse<PaymentResponseDto>> checkout(
            @Valid @RequestBody CheckoutRequestDto dto,
            Authentication authentication,
            HttpServletRequest request
    ) {
        PaymentResponseDto response =
                checkoutService.checkout(authentication.getName(), dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order created successfully",
                        response,
                        request.getRequestURI(),
                        201
                )
        );
    }


}
