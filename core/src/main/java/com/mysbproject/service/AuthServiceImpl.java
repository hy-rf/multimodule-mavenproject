package com.mysbproject.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysbproject.dao.User.UserDao;
import com.mysbproject.model.User;
import com.mysbproject.util.PasswordUtils;

@Service
public class AuthServiceImpl implements AuthService {

  @Autowired
  private UserDao userDao;

  @Override
  public String registerUser(String username, String password) {
    // Logic to register a user
    return "User registered successfully";
  }

  @Override
  public String loginUser(String username, String password) {
    Optional<User> userOpt = userDao.findByUsername(username);
    if (userOpt.isPresent() == false) {
      return "User not found";
    }
    User user = userOpt.get();
    if (!PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
      return "Invalid password";
    }
    return "User logged in successfully";
  }

  @Override
  public void logoutUser() {
    // Logic to log out a user
  }
}