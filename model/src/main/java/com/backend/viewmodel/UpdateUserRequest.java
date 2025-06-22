package com.backend.viewmodel;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class UpdateUserRequest {

  @NotNull(message = "One or more fields is blank")
  private Long id;

  @NotBlank(message = "One or more fields is blank")
  private String username;

  @NotBlank(message = "One or more fields is blank")
  private String email;

  @NotBlank(message = "One or more fields is blank")
  private String passwordHash;

  @NotBlank(message = "One or more fields is blank")
  private String fullName;

  @NotNull(message = "One or more fields is blank")
  private Boolean isActive;

  @NotNull(message = "One or more fields is blank")
  private Boolean emailVerified;

  @NotNull(message = "One or more fields is blank")
  private Boolean twoFactorEnabled;

  @NotBlank(message = "One or more fields is blank")
  private String twoFactorSecret;

  @NotNull(message = "One or more fields is blank")
  private LocalDateTime createdAt;

  @NotNull(message = "One or more fields is blank")
  private LocalDateTime updatedAt;

  @NotNull(message = "One or more fields is blank")
  private List<Long> roleIds;
}
