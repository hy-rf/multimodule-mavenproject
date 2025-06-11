package com.mysbproject.service;

import com.mysbproject.model.User;

public interface UserService {
  User[] getAllUsers();

  User getUserById(Long id);

  void saveExampleUser();
}
