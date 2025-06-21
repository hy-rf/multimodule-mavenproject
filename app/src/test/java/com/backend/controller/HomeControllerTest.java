package com.backend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.common.JwtUtils;
import com.backend.service.CustomUserDetailsService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@WebMvcTest(HomeController.class)
public class HomeControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private JwtUtils jwtUtils;

  @MockitoBean
  private CustomUserDetailsService customUserDetailsService;

  @Test
  @WithMockUser
  public void testHomePage() throws Exception {
    mockMvc.perform(get("/home")).andExpect(status().isOk());
  }

  // @SpringBootApplication(scanBasePackages = "com.backend")
  // static class TestConfiguration {
  // }

}
