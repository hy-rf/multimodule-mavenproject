package com.backend.repository;

import org.springframework.stereotype.Repository;

import com.backend.model.Class;

@Repository
public interface ClassRepository extends BaseRepository<Class, Long> {

  // Custom query methods can be defined here if needed
  // For example:
  // List<Grade> findByStudentId(Long studentId);

  // Additional methods can be added as needed

}
