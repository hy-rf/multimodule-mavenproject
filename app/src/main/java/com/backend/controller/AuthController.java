package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.common.JwtUtils;
import com.backend.dto.auth.LoginResult;
import com.backend.dto.auth.RefreshResult;
import com.backend.dto.auth.RefreshStatus;
import com.backend.dto.auth.RegisterResult;
import com.backend.service.AuthService;
import com.backend.service.LoginRateLimiterService;
import com.backend.viewmodel.LoginRequest;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@RestController
public class AuthController {

  @Autowired
  private JwtUtils jwtUtils;

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
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(3600);
        // Uncomment if using HTTPS
        // cookie.setSecure(true);
        response.addCookie(tokenCookie);
        String refreshToken = result.getRefresh();
        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(3600);
        // Uncomment if using HTTPS
        // cookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        yield "Login successful";
      }
      case USER_NOT_FOUND -> "Login failed";
      case INVALID_PASSWORD -> "Login failed";
      case ERROR -> "Login failed";
      default -> "Login failed";
    };
  }

  @PostMapping("/refresh")
  public String refresh(HttpServletRequest request, HttpServletResponse response) {
    String token = jwtUtils.resolveToken(request);
    String refreshToken = jwtUtils.resolveRefreshToken(request);
    RefreshResult result = authService.refreshToken(token, refreshToken);
    if (result.getRefreshStatus() == RefreshStatus.FAIL) {
      return "Fail";
    }
    Cookie tokenCookie = new Cookie("token", result.getNewToken());
    tokenCookie.setHttpOnly(true);
    tokenCookie.setPath("/");
    tokenCookie.setMaxAge(3600);
    // Uncomment if using HTTPS
    // tokenCookie.setSecure(true);
    response.addCookie(tokenCookie);
    Cookie refreshTokenCookie = new Cookie("refresh", result.getNewFreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(60000);
    // Uncomment if using HTTPS
    // refreshTokenCookie.setSecure(true);
    response.addCookie(refreshTokenCookie);

    return "Token refreshed successfully";
  }

  @PostMapping("/logout")
  public String logout() {
    // Logic for user logout
    return "User logged out successfully";
  }

}
