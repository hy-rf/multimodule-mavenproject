package com.backend.repository.cache;

import java.time.Duration;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public interface CacheBaseRepository<T, ID> {
  Optional<T> findById(ID id);

  T save(ID id, T entity);

  void deleteById(ID id);

  void expire(ID id, Duration duration);

  long increment(ID id);
}