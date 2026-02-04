package com.miniapp.authsystem.service;

import com.miniapp.authsystem.dto.AuthResponse;
import com.miniapp.authsystem.dto.LoginRequest;
import com.miniapp.authsystem.dto.RegisterRequest;
import com.miniapp.authsystem.entity.User;
import com.miniapp.authsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public AuthResponse registerUser(RegisterRequest request) {
        // Check if email already exists
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already registered");
        }

        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPasswordHash(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new AuthResponse(
                null,
                savedUser.getUserId(),
                savedUser.getEmail(),
                "Registration successful"
        );
    }

    public AuthResponse authenticateUser(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            throw new RuntimeException("Invalid email or password");
        }

        String token = "token_" + user.getUserId();

        return new AuthResponse(
                token,
                user.getUserId(),
                user.getEmail(),
                "Login successful"
        );
    }
}