package com.mysbproject.dto.auth;

import lombok.Data;

@Data
public class RefreshResult {
  RefreshStatus refreshStatus;
  String newToken;
  String newFreshToken;
}
