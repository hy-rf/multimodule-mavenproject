package com.backend.dto.post;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorDto {
  private Long id;
  private String username;
  private boolean emailVerified;
}