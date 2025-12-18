package com.sandeep.E_Commerce_Project.Dtos.Product;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductResponseDto {
    private String id;
    private String name;
    private String description;

    private BigDecimal price;
    private Integer stock;


    private String imageUrl;
    private String category;
}
