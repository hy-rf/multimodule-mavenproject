package com.backend.repository.cache;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class CacheBaseRepositoryFactory {

  private final StringRedisTemplate redisTemplate;
  private final boolean redisAvailable;

  public CacheBaseRepositoryFactory(StringRedisTemplate redisTemplate) {
    this.redisTemplate = redisTemplate;
    boolean available;
    try {
      redisTemplate.opsForValue().get("health_check");
      available = true;
      log.warn("Redis is available. Using RedisBaseRepository.");
    } catch (Exception ex) {
      available = false;
      log.warn("Redis is NOT available. Using InMemoryCacheRepository.");
    }
    this.redisAvailable = available;
  }

  public <K, V> CacheBaseRepository<K, V> getRepository(Class<K> keyClass, Class<V> valueClass) {
    if (redisAvailable) {
      // You may need to use a RedisTemplate<K, V> for full type support
      return (CacheBaseRepository<K, V>) new RedisBaseRepository<String, String>(redisTemplate);
    } else {
      return new InMemoryCacheRepository<>();
    }
  }
}