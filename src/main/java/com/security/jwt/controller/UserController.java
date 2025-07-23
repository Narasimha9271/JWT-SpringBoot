package com.security.jwt.controller;

import com.security.jwt.entity.AuthRequest;
import com.security.jwt.entity.UserInfo;
import com.security.jwt.service.JwtService;
import com.security.jwt.service.UserInfoService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

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
    public String addNewUser(@RequestBody UserInfo userInfo) {
        return userInfoService.addUser(userInfo);
    }

    @PostMapping("/generateToken")
    public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(authRequest.getUsername());
        } else {
            throw new UsernameNotFoundException("Invalid user request!");
        }
    }



    @GetMapping("/user/userProfile")
    public String userProfile() {
        return "Welcome to the User Profile!";
    }

    @GetMapping("/admin/adminProfile")
    public String adminProfile() {
        return "Welcome to the Admin Profile!";
    }
}
