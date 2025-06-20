package com.backend.service;

import java.util.List;

import com.backend.dto.grade.AddGradeResult;
import com.backend.model.Grade;

public interface GradeService {
  List<Grade> getGrades();

  AddGradeResult addGrade(String name);
}
