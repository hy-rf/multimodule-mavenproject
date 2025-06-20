package com.backend.service;

import com.backend.dto.auth.LoginResult;
import com.backend.dto.auth.RefreshResult;
import com.backend.dto.auth.RegisterResult;

public interface AuthService {
  RegisterResult registerUser(String username, String password);

  LoginResult loginUser(String username, String password);

  void logoutUser();

  RefreshResult refreshToken(String token, String refreshToken);
}
