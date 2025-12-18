package com.sandeep.E_Commerce_Project.Repositories;

import com.sandeep.E_Commerce_Project.Models.Order;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.math.BigDecimal;
import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {

    // 🔹 User orders
    List<Order> findByUserId(String userId);

    // 🔹 Admin: count by status
    long countByStatus(OrderStatus status);

    // ✅ Revenue = PAID + SHIPPED + DELIVERED
    List<Order> findByStatusIn(List<OrderStatus> statuses);
}
