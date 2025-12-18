package com.sandeep.E_Commerce_Project.Services.admin;

import com.sandeep.E_Commerce_Project.Dtos.admin.AdminUserResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Mappers.UserMapper;
import com.sandeep.E_Commerce_Project.Models.Role;
import com.sandeep.E_Commerce_Project.Models.User;
import com.sandeep.E_Commerce_Project.Repositories.OrderRepository;
import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdminUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final OrderRepository orderRepository;

    public AdminUserService(UserRepository userRepository, UserMapper userMapper, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.orderRepository = orderRepository;
    }

    public List<AdminUserResponseDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> userMapper.toDto(user, orderRepository))
                .toList();
    }

    public AdminUserResponseDto getUserById(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return userMapper.toDto(user, orderRepository);
    }

    public void deleteUser(String userId) {
        if (!userRepository.existsById(userId)) throw new ResourceNotFoundException("User not found");
        userRepository.deleteById(userId);
    }



    public AdminUserResponseDto changeUserRole(String userId, String roleName) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        // Logic: Replace roles based on the string sent from frontend
        Set<Role> roles = new HashSet<>();
        if ("ADMIN".equalsIgnoreCase(roleName)) {
            roles.add(Role.ROLE_ADMIN);
        } else {
            roles.add(Role.ROLE_USER);
        }
        user.setRoles(roles);

        return userMapper.toDto(userRepository.save(user), orderRepository);
    }
}