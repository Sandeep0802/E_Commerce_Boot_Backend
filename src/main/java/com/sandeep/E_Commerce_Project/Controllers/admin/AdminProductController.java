package com.sandeep.E_Commerce_Project.Controllers.admin;

import com.sandeep.E_Commerce_Project.Dtos.Product.ProductRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.Product.ProductResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.admin.AdminProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminProductService adminProductService;

    public AdminProductController(AdminProductService adminProductService) {
        this.adminProductService = adminProductService;
    }

    // 1️⃣ CREATE PRODUCT
    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponseDto>> createProduct(
            @Valid @RequestBody ProductRequestDto dto,
            HttpServletRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product created successfully",
                        adminProductService.createProduct(dto),
                        request.getRequestURI(),
                        201
                )
        );
    }

    // 2️⃣ GET ALL PRODUCTS
    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductResponseDto>>> getAllProducts(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Products fetched successfully",
                        adminProductService.getAllProducts(),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 3️⃣ GET PRODUCT BY ID
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> getProductById(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product fetched successfully",
                        adminProductService.getProductById(id),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 4️⃣ UPDATE PRODUCT
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponseDto>> updateProduct(
            @PathVariable String id,
            @Valid @RequestBody ProductRequestDto dto,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product updated successfully",
                        adminProductService.updateProduct(id, dto),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 5️⃣ DELETE PRODUCT
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteProduct(
            @PathVariable String id,
            HttpServletRequest request
    ) {
        adminProductService.deleteProduct(id);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Product deleted successfully",
                        null,
                        request.getRequestURI(),
                        200
                )
        );
    }
}
