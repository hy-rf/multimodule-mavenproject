package com.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.common.JwtUtils;
import com.backend.service.CustomUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test")
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private JwtUtils jwtUtils;

  @MockBean
  private CustomUserDetailsService customUserDetailsService;

  @Test
  @WithMockUser
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/"));
  }

  @SpringBootApplication(scanBasePackages = "com.backend")
  static class TestConfiguration {
  }

}
