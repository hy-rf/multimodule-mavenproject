package com.backend.repository;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.backend.model.Role;

@Repository
public interface RoleRepository extends BaseRepository<Role, Long> {

  Optional<Role> findByName(String string);

}