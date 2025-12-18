package com.sandeep.E_Commerce_Project.Services.checkout;

import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentResponseDto;
import com.sandeep.E_Commerce_Project.Models.Order;
import com.sandeep.E_Commerce_Project.Models.PaymentMode;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final RazorpayService razorpayService;
    private final DummyPaymentService dummyPaymentService;

    public PaymentService(RazorpayService razorpayService,
                          DummyPaymentService dummyPaymentService) {
        this.razorpayService = razorpayService;
        this.dummyPaymentService = dummyPaymentService;
    }

    public PaymentResponseDto createPayment(Order order, PaymentMode mode) {

        if (mode == PaymentMode.RAZORPAY) {
            return razorpayService.createRazorpayOrder(order);
        } else {
            return dummyPaymentService.createDummyPayment(order);
        }
    }

}
