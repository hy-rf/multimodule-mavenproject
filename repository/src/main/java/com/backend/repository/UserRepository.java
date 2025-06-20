package com.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backend.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  // @EntityGraph(attributePaths = "roles")
  Optional<User> findByUsername(String username);

}