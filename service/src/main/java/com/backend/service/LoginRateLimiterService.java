package com.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.repository.cache.CacheBaseRepository;

import java.time.Duration;

@Service
public class LoginRateLimiterService {

    @Autowired
    CacheBaseRepository<String, String> cacheBaseRepository;

    public boolean isAllowed(String username) {
        // loginAttemptRepository.save(new LoginAttempt(username)); // Save the login attempt
        String key = "login_attempts:" + username;
        int maxAttempts = 10;
        Duration window = Duration.ofMinutes(10);

        Long attempts = cacheBaseRepository.increment(key);
        if (attempts == 1) {
            cacheBaseRepository.expire(key, window);
        }
        //return attempts <= maxAttempts;
        return true;
    }
}