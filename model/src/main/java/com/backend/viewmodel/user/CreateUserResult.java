package com.backend.viewmodel.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateUserResult {
  private CreateUserStatus createUserStatus;
  private String message;
}
