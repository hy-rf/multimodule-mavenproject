package com.mysbproject.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.mysbproject.model.Grade;

@ActiveProfiles("test")
@SpringBootTest
public class GradeServiceTest {

  @MockitoBean
  private GradeService gradeService;

  @Test
  public void getGrades() {
    List<Grade> grades = gradeService.getGrades();
    System.out.println(grades.size());
  }

  @SpringBootApplication(scanBasePackages = "com.mysbproject")
  // @EntityScan(basePackages = "com.mysbproject.model")
  static class TestConfiguration {
  }
}
