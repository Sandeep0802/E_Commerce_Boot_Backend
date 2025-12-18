package com.sandeep.E_Commerce_Project.Services.checkout;

import com.sandeep.E_Commerce_Project.Dtos.checkout.CheckoutRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Models.*;
import com.sandeep.E_Commerce_Project.Repositories.CartRepository;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final PaymentService paymentService;

    public CheckoutService(CartRepository cartRepository,
                           OrderRepository orderRepository,
                           PaymentService paymentService) {
        this.cartRepository = cartRepository;
        this.orderRepository = orderRepository;
        this.paymentService = paymentService;
    }


    public PaymentResponseDto checkout(String userId, CheckoutRequestDto dto) {

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart is empty"));

        Order order = new Order();
        order.setUserId(userId);
        order.setAddressId(dto.getAddressId());
        order.setStatus(OrderStatus.PENDING_PAYMENT);
        order.setItems(cart.getItems().stream().map(ci -> {
            OrderItem oi = new OrderItem();
            oi.setProductId(ci.getProductId());
            oi.setProductName(ci.getProductName());
            oi.setPrice(ci.getPrice());
            oi.setQuantity(ci.getQuantity());
            oi.setTotalPrice(ci.getTotalPrice());
            return oi;
        }).toList());

        order.setTotalAmount(cart.getTotalAmount());

        Order savedOrder = orderRepository.save(order);

        return paymentService.createPayment(savedOrder, dto.getPaymentMode());
    }

}
