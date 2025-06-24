package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
import com.backend.viewmodel.user.CreateUserRequest;
import com.backend.viewmodel.user.CreateUserResult;
import com.backend.viewmodel.user.CreateUserStatus;

import jakarta.validation.Valid;

@RestController
public class UserController {
  @Autowired
  private UserService userService;

  @PreAuthorize("hasRole('admin')")
  @PostMapping("/user")
  public ResponseEntity<CreateUserResult> createUser(@RequestBody @Valid CreateUserRequest createUserRequest) {
    return new ResponseEntity<CreateUserResult>(new CreateUserResult(CreateUserStatus.SUCCESS, null),
        HttpStatusCode.valueOf(200));
  }

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
  public ResponseEntity<UpdateUserResult> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
    User user = userService.getUserById(updateUserRequest.getId().longValue());

    if (updateUserRequest.getUsername() != null)
      user.setUsername(updateUserRequest.getUsername());
    if (updateUserRequest.getEmail() != null)
      user.setEmail(updateUserRequest.getEmail());
    if (updateUserRequest.getPasswordHash() != null)
      user.setPasswordHash(updateUserRequest.getPasswordHash());
    if (updateUserRequest.getFullName() != null)
      user.setFullName(updateUserRequest.getFullName());
    if (updateUserRequest.getIsActive() != null)
      user.setIsActive(updateUserRequest.getIsActive());
    if (updateUserRequest.getEmailVerified() != null)
      user.setEmailVerified(updateUserRequest.getEmailVerified());
    if (updateUserRequest.getTwoFactorEnabled() != null)
      user.setTwoFactorEnabled(updateUserRequest.getTwoFactorEnabled());
    if (updateUserRequest.getTwoFactorSecret() != null)
      user.setTwoFactorSecret(updateUserRequest.getTwoFactorSecret());
    if (updateUserRequest.getCreatedAt() != null)
      user.setCreatedAt(updateUserRequest.getCreatedAt());
    if (updateUserRequest.getUpdatedAt() != null)
      user.setUpdatedAt(updateUserRequest.getUpdatedAt());

    userService.updateUser(user);
    UpdateUserResult result = new UpdateUserResult("success");
    return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
  }
}
