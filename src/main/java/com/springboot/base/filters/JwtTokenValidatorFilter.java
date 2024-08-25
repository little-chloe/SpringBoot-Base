package com.springboot.base.filters;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.SecretKey;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtTokenValidatorFilter extends OncePerRequestFilter {

    private Environment environment;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = request.getHeader("Authorization");
        if (jwt != null && jwt.startsWith("Bearer ")) {
            try {
                String secret = environment.getProperty("jwt.secret");
                jwt = jwt.substring(7);
                SecretKey secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
                Claims claims = Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(jwt)
                        .getPayload();
                String tokenType = claims.get("token-type", String.class);
                if (!tokenType.equals("access-token")) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.setContentType("application/json");
                    response.setCharacterEncoding("UTF-8");
                    response.getWriter().write("""
                                {
                                    "message": "Token is invalid or expired"
                                }
                            """);
                    return;
                }
                String email = claims.get("email", String.class);
                List<?> authoritiesRaw = claims.get("authorities", List.class);
                List<String> authorities = authoritiesRaw.stream()
                        .filter(authority -> authority instanceof String)
                        .map(authority -> (String) authority)
                        .collect(Collectors.toList());
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null,
                        authorities.stream().map(authority -> new SimpleGrantedAuthority(authority)).toList());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception exception) {
                response.setStatus(HttpStatus.UNAUTHORIZED.value());
                response.setContentType("application/json");
                response.setCharacterEncoding("UTF-8");
                response.getWriter().write("""
                            {
                                "message": "Token is invalid or expired"
                            }
                        """);
                return;
            }
        } else {
            response.setStatus(HttpStatus.BAD_REQUEST.value());
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write("""
                        {
                            "message": "Access token is missing"
                        }
                    """);
            return;

        }
        doFilter(request, response, filterChain);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        if (request.getServletPath().equals("/api/v1/users/login")) {
            return true;
        }
        if (request.getServletPath().equals("/api/v1/users/register")) {
            return true;
        }
        return false;
    }

}
