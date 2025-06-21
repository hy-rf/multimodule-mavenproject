package com.backend.service;

import org.springframework.stereotype.Service;

@Service
public interface LoginRateLimiterService {
  boolean isAllowed(String username);
}