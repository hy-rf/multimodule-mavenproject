package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.backend.viewmodel.CurrentUserResponse;
import com.backend.viewmodel.LoginRequest;
import com.backend.viewmodel.RegisterRequest;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class AuthController {

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private AuthService authService;

  @Autowired
  private LoginRateLimiterService loginRateLimiterService;

  @PostMapping("/register")
  @Operation(summary = "Register")
  public ResponseEntity<String> signup(@Valid @RequestBody RegisterRequest registerRequest, HttpSession session,
      HttpServletResponse response) {
    RegisterResult result = authService.registerUser(registerRequest.username, registerRequest.password);
    return switch (result.getStatus()) {
      case SUCCESS -> ResponseEntity.ok().body("User registered successfully");
      case USERNAME_TAKEN -> ResponseEntity.badRequest().body("Username is already taken");
      case INVALID_PASSWORD -> ResponseEntity.badRequest().body("Invalid password format");
      case ERROR -> ResponseEntity.badRequest().body("An error occurred during registration");
      default -> ResponseEntity.badRequest().body("Unknown registration status");
    };
  }

  @PostMapping("/login")
  @Operation(summary = "Login as user")
  public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest, HttpSession session,
      HttpServletResponse response) {
    if (!loginRateLimiterService.isAllowed(loginRequest.getUsername())) {
      return ResponseEntity
          .status(HttpStatus.TOO_MANY_REQUESTS)
          .body("Too many login attempts. Please try again later.");
    }
    LoginResult result = authService.loginUser(loginRequest.getUsername(), loginRequest.getPassword());
    return switch (result.getStatus()) {
      case SUCCESS -> {
        String token = result.getToken();
        Cookie tokenCookie = new Cookie("token", token);
        tokenCookie.setHttpOnly(true);
        tokenCookie.setPath("/");
        tokenCookie.setMaxAge(600);
        tokenCookie.setAttribute("SameSite", "None");
        tokenCookie.setSecure(true);
        response.addCookie(tokenCookie);
        String refreshToken = result.getRefresh();
        Cookie refreshTokenCookie = new Cookie("refresh", refreshToken);
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setMaxAge(3600);
        refreshTokenCookie.setAttribute("SameSite", "None");
        refreshTokenCookie.setSecure(true);
        response.addCookie(refreshTokenCookie);

        yield ResponseEntity.ok("Login successful");
      }
      case USER_NOT_FOUND -> ResponseEntity.badRequest().body("F");
      case INVALID_PASSWORD -> ResponseEntity.badRequest().body("F");
      case ERROR -> ResponseEntity.badRequest().body("F");
      default -> ResponseEntity.badRequest().body("F");
    };
  }

  @GetMapping("/refresh")
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
    tokenCookie.setMaxAge(600);
    // Uncomment if using HTTPS
    tokenCookie.setSecure(true);
    response.addCookie(tokenCookie);
    Cookie refreshTokenCookie = new Cookie("refresh", result.getNewFreshToken());
    refreshTokenCookie.setHttpOnly(true);
    refreshTokenCookie.setPath("/");
    refreshTokenCookie.setMaxAge(60000);
    // Uncomment if using HTTPS
    refreshTokenCookie.setSecure(true);
    response.addCookie(refreshTokenCookie);

    return "Token refreshed successfully";
  }

  @GetMapping("/leave")
  @PreAuthorize("isAuthenticated()")
  public String logout(HttpServletResponse response) {
    Cookie tokenCookie = new Cookie("token", null);
    tokenCookie.setPath("/");
    tokenCookie.setHttpOnly(true);
    tokenCookie.setMaxAge(0); // delete immediately
    response.addCookie(tokenCookie);

    // Clear refresh cookie
    Cookie refreshCookie = new Cookie("refresh", null);
    refreshCookie.setPath("/");
    refreshCookie.setHttpOnly(true);
    refreshCookie.setMaxAge(0);
    response.addCookie(refreshCookie);

    return "User logged out successfully";
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public CurrentUserResponse getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    List<String> roles = authentication.getAuthorities()
        .stream()
        .map(auth -> auth.getAuthority())
        .toList();
    return new CurrentUserResponse(username, roles);
  }

}
