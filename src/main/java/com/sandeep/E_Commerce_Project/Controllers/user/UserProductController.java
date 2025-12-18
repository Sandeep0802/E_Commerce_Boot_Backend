package com.sandeep.E_Commerce_Project.Controllers.user;

import com.sandeep.E_Commerce_Project.Dtos.Product.ProductResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.user.UserProductService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class UserProductController {

    private final UserProductService productService;

    public UserProductController(UserProductService productService) {
        this.productService = productService;
    }

    // 1️⃣ GET ALL / FILTER PRODUCTS
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getProducts(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String search,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        productService.getProducts(category, minPrice, maxPrice, search),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 2️⃣ GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully",
                        productService.getProductById(id),
                        request.getRequestURI(),
                        200
                )
        );
    }
}
