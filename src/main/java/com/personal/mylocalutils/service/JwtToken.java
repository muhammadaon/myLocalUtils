package com.personal.mylocalutils.service;

import io.jsonwebtoken.*;

public class JwtToken {

    public static void main(String[] args) {
        String subject = "user123"; // Replace with the subject you want to associate with the token
        long expirationMillis = 3600; // 1 hour expiration time

        // Create JWT Token
        String jwtToken = JwtUtil.createJwtToken(subject, expirationMillis);
        System.out.println("JWT Token: " + jwtToken);

        // Decrypt JWT Token
        Claims claims = JwtUtil.parseJwtToken(jwtToken);
        System.out.println("Subject: " + claims.getSubject());
        System.out.println("Issued At: " + claims.getIssuedAt());
        System.out.println("Expiration: " + claims.getExpiration());
    }



}
