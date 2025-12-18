package com.sandeep.E_Commerce_Project.Dtos.cart;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotBlank
    private String productId;

    @Min(1)
    private int quantity;
}
