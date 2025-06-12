package com.mysbproject.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

  @Value("${spring.security.user.name}")
  private String adminName;

  @Value("${spring.security.user.password}")
  private String adminPassword;

  public static class LoginRequest {
    public String username;
    public String password;
  }

  @PostMapping("/signup")
  public String signup() {
    // Logic for user signup
    return "User signed up successfully";
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletResponse response) {
    if (adminName.equals(loginRequest.username) && adminPassword.equals(loginRequest.password)) {
      Cookie cookie = new Cookie("myCookie", "cookieValue");
      cookie.setPath("/");
      response.addCookie(cookie);
      session.setAttribute("role", "ADMIN");
      return "Logged in as admin";
    }
    return "Invalid credentials";
  }

  @PostMapping("/logout")
  public String logout() {
    // Logic for user logout
    return "User logged out successfully";
  }

}
