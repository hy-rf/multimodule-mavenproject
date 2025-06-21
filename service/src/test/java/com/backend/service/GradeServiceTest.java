package com.backend.service;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.backend.model.Grade;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class GradeServiceTest {

  @Mock
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
  static class TestConfiguration {
  }
}
