package com.backend.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.backend.service.GradeService;
import com.backend.service.GradeServiceImpl;

@TestConfiguration
class GradeServiceTestConfiguration {

  @Bean
  public GradeService gradeService() {
    return new GradeServiceImpl(); // or mock
  }

}
