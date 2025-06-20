package com.backend.dto.auth;

import lombok.Data;

@Data
public class RegisterResult {

  public RegisterResult(RegisterStatus status) {
    this.status = status;
  }

  private RegisterStatus status;
}
