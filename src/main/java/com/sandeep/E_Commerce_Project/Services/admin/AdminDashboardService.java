package com.sandeep.E_Commerce_Project.Services.admin;

import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import com.sandeep.E_Commerce_Project.Repositories.ProductRepository;
import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminDashboardService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public AdminDashboardService(OrderRepository orderRepository,
                                 ProductRepository productRepository,
                                 UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public Map<String, Object> getDashboardStats() {

        Map<String, Object> stats = new HashMap<>();
        stats.put("products", productRepository.count());
        stats.put("users", userRepository.count());
        stats.put("orders", orderRepository.count());
        stats.put("revenue", calculateRevenue());

        return stats;
    }

    private BigDecimal calculateRevenue() {

        return orderRepository
                .findByStatusIn(
                        List.of(
                                OrderStatus.PAID,
                                OrderStatus.SHIPPED,
                                OrderStatus.DELIVERED
                        )
                )
                .stream()
                .map(order -> order.getTotalAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
