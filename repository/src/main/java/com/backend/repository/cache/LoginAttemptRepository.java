package com.backend.repository.cache;

import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.LoginAttempt;

@RedisHash("login_attempts")
@Repository
public interface LoginAttemptRepository extends CrudRepository<LoginAttempt, String> {
}
