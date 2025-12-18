package com.sandeep.E_Commerce_Project.Controllers.admin;

import com.sandeep.E_Commerce_Project.Dtos.admin.AdminOrderDetailsDto;
import com.sandeep.E_Commerce_Project.Dtos.order.OrderResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import com.sandeep.E_Commerce_Project.Services.admin.AdminOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final AdminOrderService adminOrderService;

    public AdminOrderController(AdminOrderService adminOrderService) {
        this.adminOrderService = adminOrderService;
    }

    // 🔹 Get all orders
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getAllOrders(
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "All orders fetched",
                        adminOrderService.getAllOrders(),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(
            @PathVariable String orderId,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order fetched",
                        adminOrderService.getOrderById(orderId),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 Update order status
    @PutMapping("/{orderId}/status")
    public ResponseEntity<ApiResponse<OrderResponseDto>> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam OrderStatus status,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order status updated",
                        adminOrderService.updateOrderStatus(orderId, status),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🆕 FULL ORDER DETAILS (ADMIN)
    @GetMapping("/{orderId}/details")
    public ResponseEntity<ApiResponse<AdminOrderDetailsDto>> getOrderDetails(
            @PathVariable String orderId,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order details fetched",
                        adminOrderService.getOrderDetails(orderId),
                        request.getRequestURI(),
                        200
                )
        );
    }
}
