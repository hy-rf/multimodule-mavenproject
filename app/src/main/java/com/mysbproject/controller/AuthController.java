package com.mysbproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysbproject.service.AuthService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

  @Autowired
  private AuthService authService;

  @Value("${spring.security.user.name}")
  private String adminName;

  @Value("${spring.security.user.password}")
  private String adminPassword;

  public static class LoginRequest {
    public String username;
    public String password;
  }

  public static class RegisterRequest {
    public String username;
    public String password;
  }

  @PostMapping("/register")
  public String signup(@RequestBody RegisterRequest registerRequest, HttpSession session,
      HttpServletResponse response) {
    // Logic for user signup
    return authService.registerUser(registerRequest.username, registerRequest.password);
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletResponse response) {
    return authService.loginUser(loginRequest.username, loginRequest.password);
  }

  @PostMapping("/logout")
  public String logout() {
    // Logic for user logout
    return "User logged out successfully";
  }

}
