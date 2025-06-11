package com.mysbproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mysbproject.service.UserService;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @GetMapping("/example-user")
  public String saveExampleUser() {
    // Logic to retrieve user information
    userService.saveExampleUser();
    return "User information retrieved successfully";
  }
}
