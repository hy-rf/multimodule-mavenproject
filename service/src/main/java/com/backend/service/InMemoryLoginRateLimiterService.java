package com.backend.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryLoginRateLimiterService implements LoginRateLimiterService {

  private final Map<String, Attempt> attempts = new ConcurrentHashMap<>();
  private static final int MAX_ATTEMPTS = 5;
  private static final long WINDOW_MILLIS = 10 * 60 * 1000;

  @Override
  public boolean isAllowed(String username) {
    Attempt attempt = attempts.computeIfAbsent(username, k -> new Attempt());
    synchronized (attempt) {
      long now = System.currentTimeMillis();
      if (now - attempt.start > WINDOW_MILLIS) {
        attempt.count = 0;
        attempt.start = now;
      }
      attempt.count++;
      return attempt.count <= MAX_ATTEMPTS;
    }
  }

  private static class Attempt {
    int count = 0;
    long start = System.currentTimeMillis();
  }
}