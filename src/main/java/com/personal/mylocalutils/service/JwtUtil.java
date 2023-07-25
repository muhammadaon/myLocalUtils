package com.personal.mylocalutils.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "your-secret-key"; // Replace with your own secret key

    public static String createJwtToken(String subject, long expirationMillis) {
        SecretKey key = generateKey();

        Date now = new Date();
        Date expiration = new Date(now.getTime() + expirationMillis);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(SignatureAlgorithm.HS512, key)
                .compact();
    }

    public static Claims parseJwtToken(String jwtToken) {
        SecretKey key = generateKey();

        return Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(jwtToken)
                .getBody();
    }

    private static SecretKey generateKey() {
        byte[] apiKeySecretBytes = SECRET_KEY.getBytes(StandardCharsets.UTF_8);
        return new SecretKeySpec(apiKeySecretBytes, SignatureAlgorithm.HS512.getJcaName());
    }
}
