package com.mysbproject.common;

import com.mysbproject.dto.JwtData;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class JwtUtils {

    // Generate a JWT token with userId and roleIds
    public static String generateToken(Long userId, List<Long> roleIds, String secretKey, long expirationMillis) {
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
    public static JwtData verifyToken(String token, String secretKey) {
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
}
