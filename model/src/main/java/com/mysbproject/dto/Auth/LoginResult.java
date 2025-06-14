package com.mysbproject.dto.Auth;

import lombok.Data;

@Data
public class LoginResult {
  private String message;
  private LoginStatus status;
}
