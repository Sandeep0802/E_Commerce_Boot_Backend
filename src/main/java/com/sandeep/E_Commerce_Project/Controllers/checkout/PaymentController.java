package com.sandeep.E_Commerce_Project.Controllers.checkout;

import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentVerifyDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.checkout.PaymentVerificationService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentVerificationService paymentVerificationService;

    public PaymentController(PaymentVerificationService paymentVerificationService) {
        this.paymentVerificationService = paymentVerificationService;
    }

    // 🔹 VERIFY PAYMENT
    @PostMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyPayment(
            @RequestBody PaymentVerifyDto dto,
            HttpServletRequest request
    ) {
        paymentVerificationService.verifyPayment(dto);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Payment verified successfully",
                        "PAYMENT_SUCCESS",
                        request.getRequestURI(),
                        200
                )
        );
    }
}
