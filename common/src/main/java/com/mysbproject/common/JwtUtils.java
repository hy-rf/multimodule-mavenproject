package com.mysbproject.common;

import com.mysbproject.dto.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class JwtUtils {

    // Generate a JWT token with userId and roleIds
    public String generateToken(Long userId, List<Long> roleIds, String secretKey, long expirationMillis) {
        System.out.println("Generating JWT token for userId: " + userId + ", roleIds: " + roleIds);
        Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
        return Jwts.builder()
                .claim("userId", userId)
                .claim("roleIds", roleIds)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    // Verify JWT and extract JwtData (userId and roleIds)
    public JwtData verifyToken(String token, String secretKey) {
        try {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            Jws<Claims> jws = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            Claims claims = jws.getBody();
            Long userId = claims.get("userId", Number.class).longValue();
            List<Long> roleIds = ((List<?>) claims.get("roleIds")).stream()
                    .map(val -> ((Number) val).longValue())
                    .collect(Collectors.toList());

            return new JwtData(userId, roleIds);
        } catch (JwtException | IllegalArgumentException e) {
            return null;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public String resolveRefreshToken(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for (jakarta.servlet.http.Cookie cookie : request.getCookies()) {
                if ("refresh".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
