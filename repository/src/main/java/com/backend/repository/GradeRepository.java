package com.backend.repository;

import org.springframework.stereotype.Repository;

import com.backend.model.Grade;

@Repository
public interface GradeRepository extends BaseRepository<Grade, Long> {

}
