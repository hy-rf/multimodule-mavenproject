package com.backend.repository.cache;

import org.springframework.data.redis.core.RedisTemplate;

import java.time.Duration;
import java.util.Optional;

public class RedisBaseRepository<T, ID> implements CacheBaseRepository<T, ID> {
  private final RedisTemplate<ID, T> redisTemplate;

  public RedisBaseRepository(RedisTemplate<ID, T> redisTemplate) {
    this.redisTemplate = redisTemplate;
  }

  @Override
  public Optional<T> findById(ID id) {
    return Optional.ofNullable(redisTemplate.opsForValue().get(id));
  }

  @Override
  public T save(ID id, T entity) {
    redisTemplate.opsForValue().set(id, entity);
    return entity;
  }

  @Override
  public void deleteById(ID id) {
    redisTemplate.delete(id);
  }

  @Override
  public void expire(ID id, Duration duration) {
    redisTemplate.expire(id, duration);
  }

  @Override
  public long increment(ID id) {
    Long result = redisTemplate.opsForValue().increment(id);
    return result != null ? result : 0L;
  }
}