package com.backend.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.backend.model.Grade;

@ActiveProfiles("test")
@SpringBootTest
public class GradeServiceTest {

  @MockitoBean
  private GradeService gradeService;

  // @Test
  // public void getGrades() {
  // try {
  // List<Grade> grades = gradeService.getGrades();
  // System.out.println(grades.size());
  // } catch (Exception e) {
  // e.printStackTrace();
  // }
  // }

  @SpringBootApplication(scanBasePackages = "com.backend")
  // @EntityScan(basePackages = "com.backend.model")
  static class TestConfiguration2 {
  }
}
