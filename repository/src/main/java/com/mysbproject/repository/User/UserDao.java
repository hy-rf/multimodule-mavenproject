package com.mysbproject.repository.User;

import com.mysbproject.model.User;
import java.util.List;
import java.util.Optional;

public interface UserDao {

  Optional<User> findByUsername(String username);

  List<User> getAllUsers();

  void saveUser(User user);

  void saveExampleUser(String passwordHash);

  List<User> getUsers(String filterField, String filterValue, String sortField, boolean ascending);

  User getUserById(Long id);
}
