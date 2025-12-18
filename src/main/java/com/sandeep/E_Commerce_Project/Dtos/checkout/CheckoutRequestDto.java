package com.sandeep.E_Commerce_Project.Dtos.checkout;

import com.sandeep.E_Commerce_Project.Models.PaymentMode;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CheckoutRequestDto {

    @NotBlank
    private String addressId;

    @NotNull
    private PaymentMode paymentMode; // RAZORPAY / DUMMY
}
