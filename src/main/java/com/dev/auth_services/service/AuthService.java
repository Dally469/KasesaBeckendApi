package com.dev.auth_services.service;

import com.dev.auth_services.custom.ApiResponse;
import com.dev.auth_services.custom.JwtBlacklist;
import com.dev.auth_services.entity.User;
import com.dev.auth_services.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthService {
    @Autowired
    private  UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;
    @Autowired
    private JwtBlacklist jwtBlacklist;

    public String saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "User saved to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateOnlyToken(token);
    }

    public void validateUserToken(String user, String token) {
        jwtService.validateTokenWithUsername(token, user);
    }

    public void logout(String token) {
        jwtBlacklist.add(token);
    }


    public boolean changePassword(String userId, String oldPassword, String newPassword) {
        UUID userUUID;

        try {
            userUUID = UUID.fromString(userId);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid UUID format for userId");
        }

        User user = userRepository.findById(userUUID).orElseThrow(() -> new RuntimeException("User not found"));

        if (user != null && passwordEncoder.matches(oldPassword, user.getPassword())) {
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);
            return true;
        }
        return false;
    }
}
