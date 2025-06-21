package com.backend.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.backend.model.User;

@Repository
public interface UserRepository extends BaseRepository<User, Long> {
  // @EntityGraph(attributePaths = "roles")
  Optional<User> findByUsername(String username);

}