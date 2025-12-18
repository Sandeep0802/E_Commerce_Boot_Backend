package com.sandeep.E_Commerce_Project.Services.admin;

import com.sandeep.E_Commerce_Project.Dtos.address.AddressResponseDto;
import com.sandeep.E_Commerce_Project.Dtos.admin.AdminOrderDetailsDto;
import com.sandeep.E_Commerce_Project.Dtos.order.OrderItemDto; // Import this
import com.sandeep.E_Commerce_Project.Dtos.order.OrderResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Mappers.OrderMapper;
import com.sandeep.E_Commerce_Project.Models.Address;
import com.sandeep.E_Commerce_Project.Models.Order;
import com.sandeep.E_Commerce_Project.Models.OrderStatus;
import com.sandeep.E_Commerce_Project.Models.User;
import com.sandeep.E_Commerce_Project.Repositories.AddressRepository;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; // Import for collecting stream

@Service
public class AdminOrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;


    public AdminOrderService(OrderRepository orderRepository,
                             OrderMapper orderMapper, UserRepository userRepository, AddressRepository addressRepository) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    // 🔹 Get all orders
    public List<OrderResponseDto> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    // 🔹 Get order by ID
    public OrderResponseDto getOrderById(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        return orderMapper.toDto(order);
    }

    // 🔹 Update order status
    public OrderResponseDto updateOrderStatus(String orderId, OrderStatus status) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(status);
        return orderMapper.toDto(orderRepository.save(order));
    }

    public AdminOrderDetailsDto getOrderDetails(String orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        User user = userRepository.findById(order.getUserId()).orElse(null);
        Address address = addressRepository.findById(order.getAddressId()).orElse(null);

        AdminOrderDetailsDto dto = new AdminOrderDetailsDto();

        // Order
        dto.setOrderId(order.getId());
        dto.setStatus(order.getStatus());
        dto.setTotalAmount(order.getTotalAmount());
        dto.setCreatedAt(order.getCreatedAt());

        // Payment
        dto.setPaymentProvider(order.getPaymentProvider());
        dto.setPaymentId(order.getPaymentId());
        dto.setPaymentOrderId(order.getPaymentOrderId());

        // User
        if (user != null) {
            dto.setUserId(user.getId());
            dto.setUsername(user.getUsername());
            dto.setEmail(user.getEmail());
        }

        // Address
        if (address != null) {
            AddressResponseDto addrDto = new AddressResponseDto();
            addrDto.setId(address.getId());
            addrDto.setFullName(address.getFullName());
            addrDto.setPhone(address.getPhone());
            addrDto.setStreet(address.getStreet());
            addrDto.setCity(address.getCity());
            addrDto.setState(address.getState());
            addrDto.setPostalCode(address.getPostalCode());
            addrDto.setCountry(address.getCountry());
            addrDto.setDefaultAddress(address.isDefaultAddress());

            dto.setAddress(addrDto);
        }

        // 🔹 FIX: Map OrderItem (Entity) to OrderItemDto (DTO) manually
        List<OrderItemDto> itemDtos = order.getItems().stream().map(item -> {
            OrderItemDto itemDto = new OrderItemDto();
            itemDto.setProductId(item.getProductId());
            itemDto.setProductName(item.getProductName());
            itemDto.setPrice(item.getPrice());
            itemDto.setQuantity(item.getQuantity());
            itemDto.setTotalPrice(item.getTotalPrice());
            return itemDto;
        }).collect(Collectors.toList());

        dto.setItems(itemDtos);

        return dto;
    }
}