package com.backend.service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.backend.common.JwtUtils;
import com.backend.common.PasswordUtils;
import com.backend.repository.UserRepository;
import com.backend.repository.User.UserDao;
import com.backend.dto.JwtData;
import com.backend.dto.auth.LoginResult;
import com.backend.dto.auth.LoginStatus;
import com.backend.dto.auth.RefreshResult;
import com.backend.dto.auth.RefreshStatus;
import com.backend.dto.auth.RegisterResult;
import com.backend.dto.auth.RegisterStatus;
import com.backend.model.Role;
import com.backend.model.User;

@Service
public class AuthServiceImpl implements AuthService {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Value("${jwt.secret.refresh}")
  private String jwtSecretRefresh;

  @Autowired
  private UserRepository userRepository;

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
    Optional<User> userOpt = userRepository.findByUsername(username);
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
    List<Role> roles = user.getRoles();
    // 8. Extract role IDs from the roles set

    // 8.1 Create a stream from the roles set
    Stream<Role> roleStream = roles.stream();

    // 8.2 Map each Role object to its ID
    Stream<Long> roleIdStream = roleStream.map(Role::getId);

    // 8.3 Collect the IDs into a List
    List<Long> roleIds = roleIdStream.toList();
    String token = jwtUtils.generateToken(userId, roleIds,
        jwtSecret, 60000L);
    String refreshToken = jwtUtils.generateToken(userId, roleIds,
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
    if (jwtData.getUserId() != refreshData.getUserId()) {
      return new RefreshResult(RefreshStatus.FAIL, null, null);
    }
    // TODO Auto-generated method stub
    String newToken = jwtUtils.generateToken(jwtData.getUserId(), jwtData.getRoleIds(), jwtSecret, 60000L);
    return new RefreshResult(RefreshStatus.SUCCESS, newToken, refreshToken);
  }
}