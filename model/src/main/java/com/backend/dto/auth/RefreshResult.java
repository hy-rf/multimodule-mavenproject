package com.backend.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RefreshResult {
  RefreshStatus refreshStatus;
  String newToken;
  String newFreshToken;
}
