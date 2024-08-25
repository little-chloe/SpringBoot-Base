package com.springboot.base.utils;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;

@Component
@AllArgsConstructor
public class JwtTokenValidator {

    private final Environment environment;

    public boolean validateToken(String token) {
        try {
            String secret = environment.getProperty("jwt.secret");
            SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            String tokenType = claims.get("token-type", String.class);
            return tokenType.equals("refresh-token");
        } catch (Exception e) {
            return false;
        }
    }
}
