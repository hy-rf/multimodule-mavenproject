package com.backend.repository.cache;

import org.springframework.data.redis.core.RedisTemplate;

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
}