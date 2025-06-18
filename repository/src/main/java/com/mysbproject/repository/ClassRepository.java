package com.mysbproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mysbproject.model.Class;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {

  // Custom query methods can be defined here if needed
  // For example:
  // List<Grade> findByStudentId(Long studentId);

  // Additional methods can be added as needed

}
