package com.security.jwt.controller;

import com.security.jwt.entity.UserInfo;
import com.security.jwt.service.JwtService;
import com.security.jwt.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserInfoService userInfoService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome! This endpoint is not secure.";
    }


    @PostMapping("/addNewUser")
    public ResponseEntity<?> addNewUser(@RequestBody UserInfo userInfo) {
        // Save user
        userInfoService.addUser(userInfo);

        // Generate token after user creation
        String token = jwtService.generateToken(userInfo.getName());

        Map<String, Object> response = new HashMap<>();
        response.put("jwtToken", token);
        response.put("username", userInfo.getName());
        response.put("roles", userInfo.getRoles());
        return ResponseEntity.ok(response);

    }


    @GetMapping("/user/userProfile")
    public ResponseEntity<String> userProfile(HttpServletRequest request) {
        // Extract the Authorization header
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7); // Remove "Bearer " prefix

            String username = jwtService.extractUsername(jwt);

            return ResponseEntity.ok("Welcome " + username);
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Token");
    }


    @GetMapping("/admin/adminProfile")
    public String adminProfile() {
        return "Welcome to the Admin Profile!";
    }
}
