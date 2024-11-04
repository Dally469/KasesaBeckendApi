package com.dev.auth_services.controller;

import com.dev.auth_services.custom.ApiResponse;
import com.dev.auth_services.dto.AuthRequest;
import com.dev.auth_services.entity.User;
import com.dev.auth_services.repository.UserRepository;
import com.dev.auth_services.service.AuthService;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/create")
    public String addUser(@RequestBody User user) {
        return authService.saveUser(user);
    }




    public AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> getToken(@RequestBody User loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );

            if (authentication.isAuthenticated()) {
                // Your token generation logic here
                Optional<User> userData = userRepository.findUserByUsername(loginRequest.getUsername());

                String generatedToken = authService.generateToken(loginRequest.getUsername());  // Replace with actual token generation
                User user = userData.get();

                ApiResponse response = new ApiResponse(
                        200,
                        true,
                        "Login successful",
                        generatedToken,
                        user // You can pass the token in the data field
                );

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Authentication failed"));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new ApiResponse(401, false, "Authentication failed: " + e.getMessage()));
        }
    }

    @GetMapping("/validate")
    public ResponseEntity<ApiResponse> validateToken(@RequestParam("token") String token) {
          authService.validateToken(token);

        ApiResponse response = new ApiResponse(
                200,
                true,
                "Token is valid",
                null  // You can pass the token in the data field
        );

        return ResponseEntity.ok(response);
    }
}
