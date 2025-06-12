package com.mysbproject.dao.User;

import com.mysbproject.model.User;
import java.util.List;

public interface UserDao {
  List<User> getAllUsers();

  void saveUser(User user);

  void saveExampleUser();

  List<User> getUsers(String filterField, String filterValue, String sortField, boolean ascending);
}
