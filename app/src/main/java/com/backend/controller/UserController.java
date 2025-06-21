package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.User;
import com.backend.service.UserService;
import com.backend.viewmodel.LoginRequest;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PreAuthorize("hasRole('admin')")
  @PostMapping("/test-user")
  public String saveExampleUser(@RequestBody LoginRequest loginRequest) {
    System.out.println(loginRequest);
    userService.saveExampleUser(loginRequest.getUsername(), loginRequest.getPassword());
    return "User information retrieved successfully";
  }

  @PreAuthorize("hasRole('admin')")
  @GetMapping("/users")
  public List<User> getAllUsers() {
    // Logic to retrieve all users
    List<User> user = userService.getAllUsers();
    return user;
  }

  @PreAuthorize("isAuthenticated()")
  @GetMapping("/me")
  public Boolean getCurrentUser() {
    UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext()
        .getAuthentication().getPrincipal();
    String name = userDetails.getUsername();
    Authentication p = SecurityContextHolder.getContext().getAuthentication();
    System.out.println(p + name);
    return p.isAuthenticated();
  }

  // @PreAuthorize("isAuthenticated()")
  @GetMapping("/user/{id}")
  public User getUserById(@PathVariable Integer id) {
    // Logic to retrieve user by ID

    return userService.getUserById(id.longValue());
  }
}
