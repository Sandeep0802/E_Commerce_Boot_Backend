package com.sandeep.E_Commerce_Project.Models;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItem {

    private String productId;
    private String productName;

    private BigDecimal price;   // snapshot price
    private Integer quantity;

    private BigDecimal totalPrice; // price * quantity
}
