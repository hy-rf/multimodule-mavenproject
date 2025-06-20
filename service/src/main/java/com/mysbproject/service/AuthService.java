package com.mysbproject.service;

import com.mysbproject.dto.auth.LoginResult;
import com.mysbproject.dto.auth.RefreshResult;
import com.mysbproject.dto.auth.RegisterResult;

public interface AuthService {
  RegisterResult registerUser(String username, String password);

  LoginResult loginUser(String username, String password);

  void logoutUser();

  RefreshResult refreshToken(String token, String refreshToken);
}
