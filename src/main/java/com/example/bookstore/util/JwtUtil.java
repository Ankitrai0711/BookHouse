package com.example.bookstore.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.security.Keys;

import java.util.Date;
import java.util.Collections;
import java.security.Key;

import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // Secret key should be at least 256 bits for HS256
    private String secret = "mySecretKeyaniannkijubdjebishbynudbidbdieuenkjwu";
    private long expiration = 1000 * 60 * 60; // 1 hour

    private Key getSigningKey() {
        // Convert the string secret to a byte array key
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String email, String role) {
        String formattedRole = role.startsWith("ROLE_") ? role : "ROLE_" + role.toUpperCase();

        return Jwts.builder()
                .setSubject(email)
                .claim("authorities", Collections.singletonList(formattedRole))
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        // authorities is a List<String>, so we need to extract the first element
        Object authorities = getClaims(token).get("authorities");
        if (authorities instanceof java.util.List) {
            java.util.List<?> list = (java.util.List<?>) authorities;
            if (!list.isEmpty()) {
                return list.get(0).toString();
            }
        }
        return null;
    }

    public boolean validateToken(String token, String email) {
        try {
            return email.equals(extractEmail(token)) && !isExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private boolean isExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
