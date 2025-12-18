package com.sandeep.E_Commerce_Project.Dtos.checkout;

import lombok.Data;

@Data
public class PaymentResponseDto {

    private String orderId;          // our order id
    private String razorpayOrderId;  // razorpay order id
    private String key;              // razorpay public key
    private String amount;
    private String currency;
}
