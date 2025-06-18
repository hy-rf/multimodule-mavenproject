package com.mysbproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@ActiveProfiles("test")
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/"));
  }

  @SpringBootApplication(scanBasePackages = "com.mysbproject")
  static class TestConfiguration {
  }

}
