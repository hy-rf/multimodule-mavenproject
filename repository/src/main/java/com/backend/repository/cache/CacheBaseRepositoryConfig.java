package com.backend.repository.cache;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class CacheBaseRepositoryConfig {
  @Bean
  public CacheBaseRepository<String, String> cacheBaseRepository(StringRedisTemplate redisTemplate) {
    return new InMemoryCacheRepository<>();
    // try {
    //   // Try a simple Redis command to check connectivity
    //   redisTemplate.opsForValue().get("health_check");
    //   log.warn("Redis is available. Using RedisLoginRateLimiterService.");
    //   return new RedisBaseRepository<>(redisTemplate);
    // } catch (Exception ex) {
    //   log.warn("Redis is NOT available. Using InMemoryLoginRateLimiterService.");
    //   return new InMemoryCacheRepository<>();
    // }
  }
}
