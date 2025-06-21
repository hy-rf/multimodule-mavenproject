package com.backend.repository;

import org.springframework.stereotype.Repository;

import com.backend.model.Student;

@Repository
public interface StudentRepository extends BaseRepository<Student, Long> {

  // Custom query methods can be defined here if needed
  // For example:
  // List<Grade> findByStudentId(Long studentId);

  // Additional methods can be added as needed

}
