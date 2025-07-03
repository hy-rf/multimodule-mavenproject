package com.backend.model;

import org.springframework.data.redis.core.RedisHash;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RedisHash("login_attempts")
public class LoginAttempt {
    @Id
    private String id;
    // add fields as needed

    // getters and setters
}