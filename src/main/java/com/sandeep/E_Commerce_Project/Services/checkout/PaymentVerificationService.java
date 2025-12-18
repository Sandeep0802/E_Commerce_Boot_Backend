package com.sandeep.E_Commerce_Project.Services.checkout;

import com.razorpay.Utils;
import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentVerifyDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Models.Order;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import com.sandeep.E_Commerce_Project.Repositories.CartRepository;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import com.sandeep.E_Commerce_Project.Repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentVerificationService {

    @Value("${razorpay.key.secret}")
    private String razorpaySecret;

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    public PaymentVerificationService(OrderRepository orderRepository,
                                      CartRepository cartRepository,
                                      ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
    }

    public void verifyPayment(PaymentVerifyDto dto) {

        Order order = orderRepository.findById(dto.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        // ✅ Dummy payment support
        if ("DUMMY".equals(order.getPaymentProvider())) {
            completeOrder(order);
            return;
        }

        // 🔐 Razorpay signature verification
        try {
            String payload =
                    dto.getRazorpayOrderId() + "|" + dto.getRazorpayPaymentId();

            boolean isValid = Utils.verifySignature(
                    payload,
                    dto.getRazorpaySignature(),
                    razorpaySecret
            );

            if (!isValid) {
                throw new RuntimeException("Invalid Razorpay signature");
            }

            order.setPaymentId(dto.getRazorpayPaymentId());
            completeOrder(order);

        } catch (Exception e) {
            throw new RuntimeException("Payment verification failed");
        }
    }

    // 🔹 COMPLETE ORDER (COMMON LOGIC)
    private void completeOrder(Order order) {

        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);

        // 🔻 Reduce stock
        order.getItems().forEach(item ->
                productRepository.findById(item.getProductId())
                        .ifPresent(product -> {
                            product.setStock(
                                    product.getStock() - item.getQuantity()
                            );
                            productRepository.save(product);
                        })
        );

        // 🧹 Clear cart
        cartRepository.deleteByUserId(order.getUserId());
    }
}
