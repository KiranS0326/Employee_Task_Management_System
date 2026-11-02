package com.etms.backend.security;

import java.util.Date;
import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

    // Keep the secret key from properties, but add a fallback just in case
    @Value("${jwt.secret:defaultSecretKeyThatIsAtLeast32BytesLongForHS256Algorithm}")
    private String secret;

    // FIX: Hardcoded to exactly 24 hours (86,400,000 milliseconds)
    private final long EXPIRATION_TIME = 86400000L;

    private SecretKey getKey() {
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)
                .claim("role", role)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getKey())
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(getKey()).build()
                .parseSignedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // FIX: Now we will actually see why a token fails!
            System.out.println(">>> JWT REJECTED: " + e.getMessage());
            return false;
        }
    }
}