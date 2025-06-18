package com.mysbproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysbproject.model.Grade;
import com.mysbproject.repository.GradeRepository;

@Service
public class GradeServiceImpl implements GradeService {

  @Autowired
  private GradeRepository gradeRepository;

  @Override
  public List<Grade> getGrades() {
    return gradeRepository.findAll();
  }

}