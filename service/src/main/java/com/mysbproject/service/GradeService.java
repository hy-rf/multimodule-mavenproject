package com.mysbproject.service;

import java.util.List;

import com.mysbproject.dto.grade.AddGradeResult;
import com.mysbproject.model.Grade;

public interface GradeService {
  List<Grade> getGrades();
  AddGradeResult addGrade(String name);
}
