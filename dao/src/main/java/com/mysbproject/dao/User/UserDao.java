package com.mysbproject.dao.User;

import com.mysbproject.model.User;

public interface UserDao {
  User[] getAllUsers();

  void saveUser(User user);

  void saveExampleUser();
}
