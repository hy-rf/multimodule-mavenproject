package com.mysbproject.service;

import com.mysbproject.dto.Auth.RegisterResult;

public interface AuthService {
  RegisterResult registerUser(String username, String password);

  String loginUser(String username, String password);

  void logoutUser();
}
