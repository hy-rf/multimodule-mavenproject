package com.backend.viewmodel;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;

@Getter
public class UpdateUserRequest {

  private Long id;

  private String username;

  private String email;

  private String passwordHash;

  private String fullName;

  private Boolean isActive;

  private Boolean emailVerified;

  private Boolean twoFactorEnabled;

  private String twoFactorSecret;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private List<Long> roleIds;
}
