package com.backend.controller;

import java.util.List;

import com.backend.dto.grade.AddGradeResult;
import com.backend.viewmodel.AddGradeRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.Grade;
import com.backend.service.GradeService;
import com.backend.viewmodel.AddGradeResponse;

@RestController
public class GradeController {

  @Autowired
  private GradeService gradeService;

  @GetMapping("/grades")
  public List<Grade> getGrades() {
    return gradeService.getGrades();
  }

  @PostMapping("/grade")
  public AddGradeResponse addGrade(@RequestBody AddGradeRequest addGradeRequest) {
    AddGradeResult result = gradeService.addGrade(addGradeRequest.getName());
    return new AddGradeResponse(result.getMessage());
  }
}
