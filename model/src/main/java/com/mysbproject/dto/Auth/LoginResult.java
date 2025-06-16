package com.mysbproject.dto.Auth;

import lombok.Data;

@Data
public class LoginResult {

  public LoginResult(String message, LoginStatus status) {
    this.message = message;
    this.status = status;
  }

  private String message;
  private LoginStatus status;
}
