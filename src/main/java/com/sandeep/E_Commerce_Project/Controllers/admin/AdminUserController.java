package com.sandeep.E_Commerce_Project.Controllers.admin;

import com.sandeep.E_Commerce_Project.Dtos.admin.AdminUserResponseDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.admin.AdminUserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users") // Matches frontend base path
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminUserService adminUserService;

    public AdminUserController(AdminUserService adminUserService) {
        this.adminUserService = adminUserService;
    }

    // 1️⃣ GET ALL USERS
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminUserResponseDto>>> getAllUsers(HttpServletRequest request) {
        return ResponseEntity.ok(
                ApiResponse.success("Users fetched", adminUserService.getAllUsers(), request.getRequestURI(), 200)
        );
    }

    // 2️⃣ GET USER BY ID
    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<AdminUserResponseDto>> getUserById(
            @PathVariable String userId,
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("User fetched", adminUserService.getUserById(userId), request.getRequestURI(), 200)
        );
    }

    // 3️⃣ DELETE USER
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(
            @PathVariable String userId,
            HttpServletRequest request
    ) {
        adminUserService.deleteUser(userId);
        return ResponseEntity.ok(
                ApiResponse.success("User deleted", null, request.getRequestURI(), 200)
        );
    }

    // 5️⃣ CHANGE ROLE (Matches frontend params: { role })
    @PutMapping("/{userId}/role")
    public ResponseEntity<ApiResponse<AdminUserResponseDto>> changeRole(
            @PathVariable String userId,
            @RequestParam String role, // helper: grabs ?role=ADMIN
            HttpServletRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.success("Role updated", adminUserService.changeUserRole(userId, role), request.getRequestURI(), 200)
        );
    }
}