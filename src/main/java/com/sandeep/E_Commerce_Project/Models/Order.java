package com.sandeep.E_Commerce_Project.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Data
@Document(collection = "orders")
public class Order {

    @Id
    private String id;

    private String userId;

    private List<OrderItem> items;

    private BigDecimal totalAmount;

    private OrderStatus status;

    private String addressId;

    // Payment fields (generic)
    private String paymentProvider; // RAZORPAY / DUMMY
    private String paymentOrderId;  // Razorpay orderId
    private String paymentId;       // Razorpay paymentId

    private Instant createdAt = Instant.now();
}
