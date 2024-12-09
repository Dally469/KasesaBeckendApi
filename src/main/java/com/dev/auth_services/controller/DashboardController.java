package com.dev.auth_services.controller;

import com.dev.auth_services.custom.ApiResponse;
import com.dev.auth_services.service.AuthService; // Assuming you have an AuthService for token validation
import com.dev.auth_services.service.DashboardService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;
    private final AuthService authService;

    public DashboardController(DashboardService dashboardService, AuthService authService) {
        this.dashboardService = dashboardService;
        this.authService = authService;
    }

    @GetMapping("/summary")
    public ApiResponse getDashboardSummary(@RequestHeader("Authorization") String token) {
        try {
            authService.validateToken(token); // Validate token

            // If validation passes, fetch and return dashboard summary
            return new ApiResponse(
                    HttpStatus.OK.value(),
                    true,
                    "Dashboard summary retrieved successfully",
                    dashboardService.getDashboardSummary()
            );
        } catch (Exception e) {
            // Return an unauthorized response if token validation fails
            return new ApiResponse(
                    HttpStatus.UNAUTHORIZED.value(),
                    false,
                    "Unauthorized: Invalid token"
            );
        }
    }
}
