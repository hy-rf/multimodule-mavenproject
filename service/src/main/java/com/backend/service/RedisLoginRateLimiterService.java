package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;

public class RedisLoginRateLimiterService implements LoginRateLimiterService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    public boolean isAllowed(String username) {
        String key = "login_attempts:" + username;
        int maxAttempts = 99;
        Duration window = Duration.ofMinutes(10);

        Long attempts = redisTemplate.opsForValue().increment(key);
        if (attempts == 1) {
            redisTemplate.expire(key, window);
        }
        return attempts <= maxAttempts;
    }
}