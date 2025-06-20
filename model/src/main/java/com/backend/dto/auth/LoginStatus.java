package com.backend.dto.auth;

public enum LoginStatus {
  SUCCESS,
  USER_NOT_FOUND,
  INVALID_PASSWORD,
  USER_ALREADY_LOGGED_IN,
  RATE_LIMIT_EXCEEDED,
  ERROR
}
