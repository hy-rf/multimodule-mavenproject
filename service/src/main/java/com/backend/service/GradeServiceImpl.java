package com.backend.service;

import java.util.List;

import com.backend.dto.grade.AddGradeResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.model.Grade;
import com.backend.repository.GradeRepository;

@Service
public class GradeServiceImpl implements GradeService {

  @Autowired
  private GradeRepository gradeRepository;

  @Override
  public List<Grade> getGrades() {
    return gradeRepository.findAll();
  }

  @Override
  public AddGradeResult addGrade(String name) {
    Grade newGrade = new Grade(name);
    gradeRepository.save(newGrade);
    return new AddGradeResult("Success");
  }

}