package com.mysbproject.dto.Auth;

import lombok.Data;

@Data
public class RefreshResult {
  RefreshStatus refreshStatus;
  String newToken;
  String newFreshToken;
}
