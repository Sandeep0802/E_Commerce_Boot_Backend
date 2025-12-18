package com.sandeep.E_Commerce_Project.Dtos.admin;

import com.sandeep.E_Commerce_Project.Dtos.address.AddressResponseDto;
import com.sandeep.E_Commerce_Project.Dtos.order.OrderItemDto;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class AdminOrderDetailsDto {

    // 🔹 Order
    private String orderId;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private Instant createdAt;

    // 🔹 Payment
    private String paymentProvider;
    private String paymentId;
    private String paymentOrderId;

    // 🔹 User
    private String userId;
    private String username;
    private String email;

    // 🔹 Address
    private AddressResponseDto address;

    // 🔹 Items
    private List<OrderItemDto> items;
}
