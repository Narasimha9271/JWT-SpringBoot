package com.security.jwt.entity;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {
    private String username;
    private String password;
}