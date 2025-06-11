package com.mysbproject.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

  @PostMapping("/signup")
  public String signup() {
    // Logic for user signup
    return "User signed up successfully";
  }

  @PostMapping("/login")
  public String login() {
    // Logic for user login
    return "User logged in successfully";
  }

  @PostMapping("/logout")
  public String logout() {
    // Logic for user logout
    return "User logged out successfully";
  }

}
