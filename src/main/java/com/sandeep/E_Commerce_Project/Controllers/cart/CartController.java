package com.sandeep.E_Commerce_Project.Controllers.cart;

import com.sandeep.E_Commerce_Project.Dtos.cart.CartItemRequestDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Models.Cart;
import com.sandeep.E_Commerce_Project.Services.cart.CartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasRole('USER')")

public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // 🔹 ADD TO CART
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<Cart>> addToCart(
            @Valid @RequestBody CartItemRequestDto dto,
            Authentication auth,
            HttpServletRequest request
    ) {
        String userId = auth.getName();

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Item added to cart",
                        cartService.addToCart(userId, dto.getProductId(), dto.getQuantity()),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 UPDATE QUANTITY (+ / -)
    @PutMapping("/update")
    public ResponseEntity<ApiResponse<Cart>> updateQuantity(
            @Valid @RequestBody CartItemRequestDto dto,
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cart updated successfully",
                        cartService.updateQuantity(auth.getName(), dto.getProductId(), dto.getQuantity()),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 VIEW CART
    @GetMapping
    public ResponseEntity<ApiResponse<Cart>> getCart(
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cart fetched",
                        cartService.getCart(auth.getName()),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 REMOVE ITEM
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse<Cart>> removeItem(
            @PathVariable String productId,
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Item removed from cart",
                        cartService.removeItem(auth.getName(), productId),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 CLEAR CART
    @DeleteMapping("/clear")
    public ResponseEntity<ApiResponse<Void>> clearCart(
            Authentication auth,
            HttpServletRequest request
    ) {
        cartService.clearCart(auth.getName());

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Cart cleared",
                        null,
                        request.getRequestURI(),
                        200
                )
        );
    }
}
