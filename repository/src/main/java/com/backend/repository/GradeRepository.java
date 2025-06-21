package com.backend.repository;

import org.springframework.stereotype.Repository;

import com.backend.model.Grade;

@Repository
public interface GradeRepository extends BaseRepository<Grade, Long> {

  // Custom query methods can be defined here if needed
  // For example:
  // List<Grade> findByStudentId(Long studentId);

  // Additional methods can be added as needed

}
