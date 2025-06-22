package com.backend.viewmodel;

import jakarta.validation.constraints.NotBlank;

public class RegisterRequest {
  @NotBlank(message = "Username must not be blank")
  public String username;

  @NotBlank(message = "Password must not be blank")
  public String password;
}