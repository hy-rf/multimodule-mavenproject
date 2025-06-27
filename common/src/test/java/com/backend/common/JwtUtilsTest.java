package com.backend.common;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JwtUtilsTest {

    @Autowired
    private final JwtUtils jwtUtils = new JwtUtils();

    private static final String SECRET_KEY = "mySuperSecretKeyForJwtTesting1234567890"; // should be at least 32 bytes for
    // HS256

    @Test
    void testGenerateAndVerifyToken() {
        Long userId = 42L;
        List<Long> roleIds = Arrays.asList(1L, 2L, 3L);
        long expirationMillis = 60_000; // 1 minute

        String token = jwtUtils.generateToken(userId, roleIds, SECRET_KEY, expirationMillis);
        assertNotNull(token);

        JwtData jwtData = jwtUtils.verifyToken(token, SECRET_KEY);
        assertNotNull(jwtData);
        assertEquals(userId, jwtData.getUserId());
        assertEquals(roleIds, jwtData.getRoleIds());
    }

    @Test
    void testVerifyToken_InvalidToken() {
        String invalidToken = "invalid.jwt.token";
        JwtData jwtData = jwtUtils.verifyToken(invalidToken, SECRET_KEY);
        assertNull(jwtData);
    }

    @Test
    void testVerifyToken_WrongSecret() {
        Long userId = 99L;
        List<Long> roleIds = Arrays.asList(5L, 6L);
        String token = jwtUtils.generateToken(userId, roleIds, SECRET_KEY, 60_000);

        String wrongSecret = "anotherSecretKeyThatIsWrong1234567890";
        JwtData jwtData = jwtUtils.verifyToken(token, wrongSecret);
        assertNull(jwtData);
    }
}
