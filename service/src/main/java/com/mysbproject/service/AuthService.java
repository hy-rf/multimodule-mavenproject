package com.mysbproject.service;

import com.mysbproject.dto.Auth.LoginResult;
import com.mysbproject.dto.Auth.RefreshResult;
import com.mysbproject.dto.Auth.RegisterResult;

public interface AuthService {
  RegisterResult registerUser(String username, String password);

  LoginResult loginUser(String username, String password);

  void logoutUser();

  RefreshResult refreshToken(String token, String refreshToken);
}
