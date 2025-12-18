package com.sandeep.E_Commerce_Project.Models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@Document(collection = "carts")
public class Cart {

    @Id
    private String id;

    private String userId;

    private List<CartItem> items = new ArrayList<>();

    private BigDecimal totalAmount = BigDecimal.ZERO;
}
