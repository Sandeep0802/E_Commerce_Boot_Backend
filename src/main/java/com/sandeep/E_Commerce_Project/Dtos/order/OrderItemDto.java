package com.sandeep.E_Commerce_Project.Dtos.order;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemDto {

    private String productId;
    private String productName;
    private BigDecimal price;
    private Integer quantity;
    private BigDecimal totalPrice;
}
