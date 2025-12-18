package com.sandeep.E_Commerce_Project.Services.user;


import com.sandeep.E_Commerce_Project.Dtos.user.UserProfileDto;
import com.sandeep.E_Commerce_Project.Exceptions.ResourceNotFoundException;
import com.sandeep.E_Commerce_Project.Models.User;
import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserProfileDto getProfile(String userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        UserProfileDto dto = new UserProfileDto();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setEmail(user.getEmail());
        dto.setRole(
                user.getRoles().iterator().next().name().replace("ROLE_", "")
        );
        return dto;
    }

}
