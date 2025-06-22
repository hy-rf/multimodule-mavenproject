package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.User;
import com.backend.service.UserService;
import com.backend.viewmodel.CurrentUserResponse;
import com.backend.viewmodel.LoginRequest;
import com.backend.viewmodel.UpdateUserRequest;
import com.backend.viewmodel.UpdateUserResult;

import jakarta.validation.Valid;

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
  public CurrentUserResponse getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    String username = authentication.getName();
    List<String> roles = authentication.getAuthorities()
        .stream()
        .map(auth -> auth.getAuthority())
        .toList();
    return new CurrentUserResponse(username, roles);
  }

  @PreAuthorize("hasRole('admin')")
  @GetMapping("/user/{id}")
  public User getUserById(@PathVariable Integer id) {
    // Logic to retrieve user by ID

    return userService.getUserById(id.longValue());
  }

  @PreAuthorize("hasRole('admin')")
  @PutMapping("/user")
  public ResponseEntity<UpdateUserResult> updateUser(@Valid @RequestBody UpdateUserRequest UpdateUserRequest) {
    UpdateUserResult result = new UpdateUserResult("success");
    return new ResponseEntity<UpdateUserResult>(result, HttpStatusCode.valueOf(200));
  }
}
