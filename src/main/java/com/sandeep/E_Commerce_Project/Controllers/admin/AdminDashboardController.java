package com.sandeep.E_Commerce_Project.Controllers.admin;

import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.admin.AdminDashboardService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/dashboard")
@PreAuthorize("hasRole('ADMIN')")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;

    public AdminDashboardController(AdminDashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ApiResponse<Map<String, Object>> dashboard() {

        Map<String, Object> data = dashboardService.getDashboardStats();

        return ApiResponse.success(
                "Dashboard stats fetched successfully",
                data,
                "/api/admin/dashboard",
                200
        );
    }
}
