package com.mysbproject.dto.Auth;

import lombok.Data;

@Data
public class LoginResult {

  public LoginResult(String message, LoginStatus status, String token) {
    this.message = message;
    this.status = status;
    this.token = token; // Default to null, can be set later if needed
  }

  private String message;
  private LoginStatus status;
  private String token; // Optional, if you want to return a token on successful login
}
