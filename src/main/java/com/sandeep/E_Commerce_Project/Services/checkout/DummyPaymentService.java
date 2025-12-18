package com.sandeep.E_Commerce_Project.Services.checkout;

import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentResponseDto;
import com.sandeep.E_Commerce_Project.Models.Order;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class DummyPaymentService {

    private final OrderRepository orderRepository;

    public DummyPaymentService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    /**
     * Create dummy payment order
     * (similar to Razorpay order creation)
     */
    public PaymentResponseDto createDummyPayment(Order order) {

        order.setPaymentProvider("DUMMY");
        order.setPaymentOrderId("DUMMY_" + System.currentTimeMillis());
        order.setStatus(OrderStatus.PENDING_PAYMENT);

        orderRepository.save(order);

        PaymentResponseDto response = new PaymentResponseDto();
        response.setOrderId(order.getId());
        response.setAmount(order.getTotalAmount().toString());
        response.setCurrency("INR");

        return response;
    }

    /**
     * Verify dummy payment (always success)
     */
    public void verifyDummyPayment(Order order) {
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
    }
}
