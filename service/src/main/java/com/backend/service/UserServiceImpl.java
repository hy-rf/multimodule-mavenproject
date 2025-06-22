package com.backend.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

import com.backend.common.PasswordUtils;
import com.backend.model.User;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

  @Autowired
  UserRepository userRepository;

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
    log.info("Fetching all users from the database.");
    return userRepository.findAll();
  }

  @Override
  public User getUserById(Long id) {
    return userRepository.findById(id).get();
  }

  @Override
  public void saveExampleUser(String userName, String password) {
    String hash = PasswordUtils.hashPassword(password);
    User exUser = new User();
    exUser.setUsername(userName);
    exUser.setPasswordHash(hash);
    userRepository.save(exUser);
  }

  // Additional methods can be added here for user-related operations.
}
