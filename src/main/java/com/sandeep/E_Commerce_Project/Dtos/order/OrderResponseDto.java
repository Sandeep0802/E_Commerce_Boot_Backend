package com.sandeep.E_Commerce_Project.Dtos.order;

import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
public class OrderResponseDto {

    private String id;
    private OrderStatus status;
    private BigDecimal totalAmount;
    private String paymentProvider;
    private Instant createdAt;

    private String addressId;
    private List<OrderItemDto> items;
}
