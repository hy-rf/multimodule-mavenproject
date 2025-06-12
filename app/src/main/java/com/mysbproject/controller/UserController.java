package com.mysbproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysbproject.model.User;
import com.mysbproject.service.UserService;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PreAuthorize("hasRole('ADMIN')")
  @GetMapping("/example-user")
  public String saveExampleUser() {
    // Logic to retrieve user information
    userService.saveExampleUser();
    return "User information retrieved successfully";
  }

  @GetMapping("/users")
  public List<User> getAllUsers() {
    // Logic to retrieve all users
    return userService.getAllUsers();
  }
}
