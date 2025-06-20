package com.backend.service;

import java.util.List;

import com.backend.model.User;

public interface UserService {
  List<User> getAllUsers();

  User getUserById(Long id);

  void saveExampleUser();
}
