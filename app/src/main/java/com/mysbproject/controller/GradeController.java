package com.mysbproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysbproject.model.Grade;
import com.mysbproject.service.GradeService;
import com.mysbproject.viewmodel.AddGradeResponse;

@RestController
public class GradeController {

  @Autowired
  private GradeService gradeService;

  @GetMapping("/grades")
  public List<Grade> getGrades() {
    return gradeService.getGrades();
  }

  @PostMapping("/grade")
  public AddGradeResponse addGrade() {
    return new AddGradeResponse("null");
  }
}
