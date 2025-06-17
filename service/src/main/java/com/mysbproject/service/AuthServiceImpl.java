package com.mysbproject.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mysbproject.common.JwtUtils;
import com.mysbproject.repository.User.UserDao;
import com.mysbproject.dto.Auth.LoginResult;
import com.mysbproject.dto.Auth.LoginStatus;
import com.mysbproject.dto.Auth.RegisterResult;
import com.mysbproject.dto.Auth.RegisterStatus;
import com.mysbproject.model.Role;
import com.mysbproject.model.User;
import com.mysbproject.util.PasswordUtils;

@Service
public class AuthServiceImpl implements AuthService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Autowired
  private UserDao userDao;

  @Autowired
  private JwtUtils jwtUtils;

  @Override
  public RegisterResult registerUser(String username, String password) {
    if (userDao.findByUsername(username).isPresent()) {
      return new RegisterResult(RegisterStatus.USERNAME_TAKEN);
    }
    String hash = PasswordUtils.hashPassword(password);

    User user = new User();
    user.setUsername(username);
    user.setPasswordHash(hash);

    userDao.saveUser(user);
    return new RegisterResult(RegisterStatus.SUCCESS);
  }

  @Override
  public LoginResult loginUser(String username, String password) {
    Optional<User> userOpt = userDao.findByUsername(username);
    if (userOpt.isPresent() == false) {
      return new LoginResult("User not found", LoginStatus.USER_NOT_FOUND, null);
    }
    User user = userOpt.get();
    if (!PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
      return new LoginResult("Invalid password", LoginStatus.INVALID_PASSWORD, null);
    }
    // Logic to set user session or token can be added here
    // For example, you might want to set a session attribute or generate a JWT
    // token
    System.out.println("User " + user.getUsername() + " logged in successfully.");
    Long userId = user.getId();
    Set<Role> roles = user.getRoles();
    String token = jwtUtils.generateToken(userId, roles.stream().map(Role::getId).toList(),
        jwtSecret,
        3600000L);
    return new LoginResult("Login successful", LoginStatus.SUCCESS, token);
  }

  @Override
  public void logoutUser() {
    // Logic to log out a user
  }
}