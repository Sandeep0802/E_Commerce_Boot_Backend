package com.sandeep.E_Commerce_Project.Services.user;

import com.sandeep.E_Commerce_Project.Dtos.order.OrderResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Mappers.OrderMapper;
import com.sandeep.E_Commerce_Project.Models.Order;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public UserOrderService(OrderRepository orderRepository,
                            OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    // 🔹 Get all orders of logged-in user
    public List<OrderResponseDto> getMyOrders(String userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    // 🔹 Get single order
    public OrderResponseDto getMyOrderById(String userId, String orderId) {

        Order order = orderRepository.findById(orderId)
                .filter(o -> o.getUserId().equals(userId))
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return orderMapper.toDto(order);
    }
}
