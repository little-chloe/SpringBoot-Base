package com.springboot.base.utils;

import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.AllArgsConstructor;

import java.util.Date;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

@Component
@AllArgsConstructor
public class JwtTokenGenerator {

    private final Environment environment;

    public String generateAccessToken(Authentication authentication) {
        long accessTokenExpiration = environment.getProperty("jwt.access-token.expiration", Long.class);
        return generateToken(authentication, accessTokenExpiration, "access-token");
    }

    public String generateRefreshToken(Authentication authentication) {
        long refreshTokenExpiration = environment.getProperty("jwt.refresh-token.expiration", Long.class);
        return generateToken(authentication, refreshTokenExpiration, "refresh-token");
    }

    private String generateToken(Authentication authentication, long expiration, String tokenType) {
        String secret = environment.getProperty("jwt.secret");
        Date currentDate = new Date();
        SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        String token = Jwts.builder()
                .claim("email", authentication.getName())
                .claim("authorities",
                        authentication.getAuthorities()
                                .stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .claim("token-type", tokenType)
                .issuedAt(currentDate)
                .expiration(new Date(currentDate.getTime() + expiration))
                .signWith(secretKey)
                .compact();
        return token;
    }
}
