package com.backend.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.repository.UserRepository;
import com.backend.repository.User.UserDao;
import com.backend.common.PasswordUtils;
import com.backend.model.User;

@Service
public class UserServiceImpl implements UserService {
  private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

  @Autowired
  UserRepository userRepository;
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
    logger.info("Fetching all users from the database.");
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userDao.getUserById(id);
  }

  @Override
  public void saveExampleUser(String userName, String password) {
    String hash = PasswordUtils.hashPassword(password);
    userDao.saveExampleUser(userName, hash);
  }

  // Additional methods can be added here for user-related operations.
}
