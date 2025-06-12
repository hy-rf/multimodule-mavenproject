package com.mysbproject.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysbproject.dao.User.UserDao;
import com.mysbproject.model.User;

@Service
public class UserServiceImpl implements UserService {

  @Autowired
  UserDao userDao;
  // Implement methods for user management, such as creating, updating, and
  // deleting users.
  // This class will interact with the UserRepository to perform database
  // operations.

  // Example method:
  public void createUser(String username, String password) {
    // Logic to create a new user
  }

  @Override
  public List<User> getAllUsers() {
    return userDao.getAllUsers();
  }

  @Override
  public User getUserById(Long id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getUserById'");
  }

  @Override
  public void saveExampleUser() {
    // TODO Auto-generated method stub
    userDao.saveExampleUser();
  }

  // Additional methods can be added here for user-related operations.
}
