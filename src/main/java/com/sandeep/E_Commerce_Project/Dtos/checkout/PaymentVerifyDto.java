package com.sandeep.E_Commerce_Project.Dtos.checkout;

import lombok.Data;

@Data
public class PaymentVerifyDto {
    private String orderId;
    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
