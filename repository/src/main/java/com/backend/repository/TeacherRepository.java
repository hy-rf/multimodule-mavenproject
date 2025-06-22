package com.backend.repository;

import org.springframework.stereotype.Repository;

import com.backend.model.Teacher;

@Repository
public interface TeacherRepository extends BaseRepository<Teacher, Long> {

  // Custom query methods can be defined here if needed
  // For example:
  // List<Grade> findByStudentId(Long studentId);

  // Additional methods can be added as needed

}
