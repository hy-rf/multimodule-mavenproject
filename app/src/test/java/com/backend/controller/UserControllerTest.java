package com.backend.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.backend.common.JwtUtils;
import com.backend.service.CustomUserDetailsService;

@ActiveProfiles("test")
@WebMvcTest(UserControllerTest.class)
public class UserControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockitoBean
  private JwtUtils jwtUtils;

  @MockitoBean
  private CustomUserDetailsService customUserDetailsService;

  @Test
  @WithMockUser(roles = "admin")
  public void testUsersEndpoint() throws Exception {
    mockMvc.perform(get("/users"));
  }
}
