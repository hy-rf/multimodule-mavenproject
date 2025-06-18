package com.mysbproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

  // @PreAuthorize("isAuthenticated()")
  @GetMapping("/users")
  public List<User> getAllUsers() {
    // Logic to retrieve all users
    return userService.getAllUsers();
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public Boolean getCurrentUser() {
    Authentication p = SecurityContextHolder.getContext().getAuthentication();
    System.out.println(p);
    return p.isAuthenticated();
  }

  // @PreAuthorize("isAuthenticated()")
  @GetMapping("/user/{id}")
  public User getUserById(@PathVariable Integer id) {
    // Logic to retrieve user by ID

    return userService.getUserById(id.longValue());
  }
}
