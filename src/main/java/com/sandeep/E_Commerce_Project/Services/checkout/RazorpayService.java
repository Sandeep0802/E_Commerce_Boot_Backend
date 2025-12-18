package com.sandeep.E_Commerce_Project.Services.checkout;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.sandeep.E_Commerce_Project.Dtos.checkout.PaymentResponseDto;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RazorpayService {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    @Value("${razorpay.currency}")
    private String currency;

    private final OrderRepository orderRepository;

    public RazorpayService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public PaymentResponseDto createRazorpayOrder(com.sandeep.E_Commerce_Project.Models.Order order) {

        try {
            RazorpayClient client = new RazorpayClient(keyId, keySecret);

            JSONObject options = new JSONObject();
            options.put("amount", order.getTotalAmount().multiply(java.math.BigDecimal.valueOf(100))); // paise
            options.put("currency", currency);
            options.put("receipt", order.getId());

            Order razorpayOrder = client.orders.create(options);

            order.setPaymentOrderId(razorpayOrder.get("id"));
            order.setPaymentProvider("RAZORPAY");
            orderRepository.save(order);

            PaymentResponseDto response = new PaymentResponseDto();
            response.setOrderId(order.getId());
            response.setRazorpayOrderId(razorpayOrder.get("id"));
            response.setKey(keyId);
            response.setAmount(options.get("amount").toString());
            response.setCurrency(currency);

            return response;

        } catch (Exception e) {
            throw new RuntimeException("Razorpay order creation failed");
        }
    }
}
