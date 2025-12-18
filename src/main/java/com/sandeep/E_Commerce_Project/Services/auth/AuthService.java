package com.sandeep.E_Commerce_Project.Services.auth;

import com.sandeep.E_Commerce_Project.Dtos.auth.AuthRequestDto;
import com.sandeep.E_Commerce_Project.Dtos.auth.AuthResponseDto;
import com.sandeep.E_Commerce_Project.Dtos.auth.RegisterRequestDto;
import com.sandeep.E_Commerce_Project.Models.Role;
import com.sandeep.E_Commerce_Project.Models.User;
import com.sandeep.E_Commerce_Project.Repositories.UserRepository;
import com.sandeep.E_Commerce_Project.Security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final JwtService jwt;

    public AuthService(UserRepository userRepo, PasswordEncoder encoder, JwtService jwt) {
        this.userRepo = userRepo;
        this.encoder = encoder;
        this.jwt = jwt;
    }

    public void register(RegisterRequestDto req) {

        if (userRepo.existsByUsername(req.getUsername()))
            throw new IllegalArgumentException("Username already taken");

        if (userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already registered");

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(encoder.encode(req.getPassword()));
        user.setRoles(Set.of(Role.ROLE_USER));

        userRepo.save(user);
    }

    public AuthResponseDto login(AuthRequestDto req) {

        User user = userRepo.findByUsername(req.getUsernameOrEmail())
                .or(() -> userRepo.findByEmail(req.getUsernameOrEmail()))
                .orElseThrow(() -> new IllegalArgumentException("Invalid login"));

        if (!encoder.matches(req.getPassword(), user.getPassword()))
            throw new IllegalArgumentException("Invalid login");

        Set<String> roles = user.getRoles()
                .stream()
                .map(Enum::name)
                .collect(Collectors.toSet());


        String token = jwt.generateToken(
                user.getId().toString(),
                user.getUsername(),
                roles
        );

        return new AuthResponseDto(token,roles);
    }
}

