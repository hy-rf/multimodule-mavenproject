package com.mysbproject.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
class GradeServiceTestConfiguration {

  @Bean
  public GradeService gradeService() {
    return new GradeServiceImpl(); // or mock
  }

}
