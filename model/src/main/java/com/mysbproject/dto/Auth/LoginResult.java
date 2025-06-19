package com.mysbproject.dto.Auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginResult {

  private String message;
  private LoginStatus status;
  private String token;
  private String refresh;
}
