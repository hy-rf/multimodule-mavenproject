package com.backend.repository.cache;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.time.Duration;

public class InMemoryCacheRepository<T, ID> implements CacheBaseRepository<T, ID> {
  private final Map<ID, T> store = new ConcurrentHashMap<>();
  private final Map<ID, Long> expiry = new ConcurrentHashMap<>();

  @Override
  public Optional<T> findById(ID id) {
    Long exp = expiry.get(id);
    if (exp != null && exp < System.currentTimeMillis()) {
      store.remove(id);
      expiry.remove(id);
      return Optional.empty();
    }
    return Optional.ofNullable(store.get(id));
  }

  @Override
  public T save(ID id, T entity) {
    store.put(id, entity);
    expiry.remove(id);
    return entity;
  }

  @Override
  public void deleteById(ID id) {
    store.remove(id);
    expiry.remove(id);
  }

  @Override
  public void expire(ID id, Duration duration) {
    if (store.containsKey(id)) {
      expiry.put(id, System.currentTimeMillis() + duration.toMillis());
    }
  }

  @Override
  public long increment(ID id) {
    Long exp = expiry.get(id);
    if (exp == null)
      exp = 1L;
    save(id, (T) exp);
    return exp == 1L ? 1L : exp + 1;
  }
}