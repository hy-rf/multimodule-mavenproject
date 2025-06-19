package com.mysbproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mysbproject.dto.Auth.LoginResult;
import com.mysbproject.dto.Auth.RegisterResult;
import com.mysbproject.service.AuthService;
import com.mysbproject.service.LoginRateLimiterService;
import com.mysbproject.viewmodel.LoginRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

  @Autowired
  private AuthService authService;

  @Autowired
  private LoginRateLimiterService loginRateLimiterService;

  public static class RegisterRequest {
    public String username;
    public String password;
  }

  @PostMapping("/register")
  public String signup(@RequestBody RegisterRequest registerRequest, HttpSession session,
      HttpServletResponse response) {
    RegisterResult result = authService.registerUser(registerRequest.username, registerRequest.password);
    return switch (result.getStatus()) {
      case SUCCESS -> "User registered successfully";
      case USERNAME_TAKEN -> "Username is already taken";
      case INVALID_PASSWORD -> "Invalid password format";
      case ERROR -> "An error occurred during registration";
      default -> "Unknown registration status";
    };
  }

  @PostMapping("/login")
  public String login(@RequestBody LoginRequest loginRequest, HttpSession session, HttpServletResponse response) {
    if (!loginRateLimiterService.isAllowed(loginRequest.getUsername())) {
      return "Too many login attempts. Please try again later.";
    }
    LoginResult result = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    return switch (result.getStatus()) {
      case SUCCESS -> {
        String token = result.getToken();
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60);
        // Uncomment if using HTTPS
        // cookie.setSecure(true);
        response.addCookie(cookie);
        response.setHeader("Authorization", "Bearer " + token);

        yield "Login successful";
      }
      case USER_NOT_FOUND -> "Login failed";
      case INVALID_PASSWORD -> "Login failed";
      case ERROR -> "Login failed";
      default -> "Login failed";
    };
  }

  @PostMapping("/refresh")
  public String refresh(HttpServletResponse response) {
    // Logic to refresh the authentication token
    String newToken = authService.refreshToken();
    Cookie cookie = new Cookie("token", newToken);
    cookie.setHttpOnly(true);
    cookie.setPath("/");
    cookie.setMaxAge(60 * 60);
    // Uncomment if using HTTPS
    // cookie.setSecure(true);
    response.addCookie(cookie);
    response.setHeader("Authorization", "Bearer " + newToken);

    return "Token refreshed successfully";
  }

  @PostMapping("/logout")
  public String logout() {
    // Logic for user logout
    return "User logged out successfully";
  }

}
