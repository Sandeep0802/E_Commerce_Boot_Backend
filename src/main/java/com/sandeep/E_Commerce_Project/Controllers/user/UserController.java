package com.sandeep.E_Commerce_Project.Controllers.user;

import com.sandeep.E_Commerce_Project.Dtos.user.UserProfileDto;
import com.sandeep.E_Commerce_Project.Exceptions.ApiResponse;
import com.sandeep.E_Commerce_Project.Services.user.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UserController
{
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/api/users/me")
    public ApiResponse<UserProfileDto> getMyProfile(
            Authentication auth,
            HttpServletRequest request
    ) {
        return ApiResponse.success(
                "Profile fetched",
                userService.getProfile(auth.getName()),
                request.getRequestURI(),
                200
        );
    }

}
