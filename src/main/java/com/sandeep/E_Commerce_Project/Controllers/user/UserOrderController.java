package com.sandeep.E_Commerce_Project.Controllers.user;

import com.sandeep.E_Commerce_Project.Dtos.order.OrderResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.user.UserOrderService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class UserOrderController {

    private final UserOrderService userOrderService;

    public UserOrderController(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    // 🔹 Get my orders
    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponseDto>>> getMyOrders(
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Orders fetched successfully",
                        userOrderService.getMyOrders(auth.getName()),
                        request.getRequestURI(),
                        200
                )
        );
    }

    // 🔹 Get order by ID
    @GetMapping("/{orderId}")
    public ResponseEntity<ApiResponse<OrderResponseDto>> getOrderById(
            @PathVariable String orderId,
            Authentication auth,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success(
                        "Order fetched successfully",
                        userOrderService.getMyOrderById(auth.getName(), orderId),
                        request.getRequestURI(),
                        200
                )
        );
    }
}
