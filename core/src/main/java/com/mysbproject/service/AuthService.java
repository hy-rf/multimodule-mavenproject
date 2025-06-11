package com.mysbproject.service;

public interface AuthService {
  String registerUser(String username, String password);

  String loginUser(String username, String password);

  void logoutUser();
}
