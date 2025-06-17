package com.mysbproject.service;

import java.util.List;

import com.mysbproject.model.User;

public interface UserService {
  List<User> getAllUsers();

  User getUserById(Long id);

  void saveExampleUser();
}
