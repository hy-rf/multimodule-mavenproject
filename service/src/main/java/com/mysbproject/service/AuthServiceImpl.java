package com.mysbproject.service;

import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.mysbproject.common.JwtUtils;
import com.mysbproject.common.PasswordUtils;
import com.mysbproject.repository.User.UserDao;
import com.mysbproject.dto.JwtData;
import com.mysbproject.dto.auth.LoginResult;
import com.mysbproject.dto.auth.LoginStatus;
import com.mysbproject.dto.auth.RefreshResult;
import com.mysbproject.dto.auth.RegisterResult;
import com.mysbproject.dto.auth.RegisterStatus;
import com.mysbproject.model.Role;
import com.mysbproject.model.User;

@Service
public class AuthServiceImpl implements AuthService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.secret.refresh}")
  private String jwtSecretRefresh;

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
      return new LoginResult("User not found", LoginStatus.USER_NOT_FOUND, null, null);
    }
    User user = userOpt.get();
    if (!PasswordUtils.verifyPassword(password, user.getPasswordHash())) {
      return new LoginResult("Invalid password", LoginStatus.INVALID_PASSWORD, null, null);
    }
    // Logic to set user session or token can be added here
    // For example, you might want to set a session attribute or generate a JWT
    // token
    System.out.println("User " + user.getUsername() + " logged in successfully.");
    Long userId = user.getId();
    Set<Role> roles = user.getRoles();
    String token = jwtUtils.generateToken(userId, roles.stream().map(Role::getId).toList(),
        jwtSecret, 60000L);
    String refreshToken = jwtUtils.generateToken(userId, roles.stream().map(Role::getId).toList(),
        jwtSecretRefresh, 3600000L);
    // TODO add token to redis
    return new LoginResult("Login successful", LoginStatus.SUCCESS, token, refreshToken);
  }

  @Override
  public void logoutUser() {
    // Logic to log out a user
  }

  @Override
  public RefreshResult refreshToken(String token, String refreshToken) {
    JwtData jwtData = jwtUtils.verifyToken(token, jwtSecret);
    JwtData refreshData = jwtUtils.verifyToken(refreshToken, jwtSecretRefresh);
    System.out.println(jwtData);
    System.out.println(refreshData);
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'refreshToken'");
  }
}