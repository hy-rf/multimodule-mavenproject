package com.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.backend.common.JwtUtils;
import com.backend.common.PasswordUtils;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import com.backend.common.JwtData;
import com.backend.dto.auth.LoginResult;
import com.backend.dto.auth.LoginStatus;
import com.backend.dto.auth.RefreshResult;
import com.backend.dto.auth.RefreshStatus;
import com.backend.dto.auth.RegisterResult;
import com.backend.dto.auth.RegisterStatus;
import com.backend.model.Role;
import com.backend.model.User;

@Service
public class AuthService {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.secret.refresh}")
    private String jwtSecretRefresh;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JwtUtils jwtUtils;

    public RegisterResult registerUser(String username, String password) {
        if (userRepository.findByUsername(username).isPresent()) {
            return new RegisterResult(RegisterStatus.USERNAME_TAKEN);
        }
        String hash = PasswordUtils.hashPassword(password);

        User user = new User();
        user.setUsername(username);
        user.setPasswordHash(hash);

        Optional<Role> userRoleOpt = roleRepository.findById(1L);
        if (userRoleOpt.isEmpty()) {
            return new RegisterResult(RegisterStatus.ERROR);
        }
        Role userRole = userRoleOpt.get();
        List<Role> roles = new ArrayList<>();
        roles.add(userRole);
        user.setRoles(roles);
        userRepository.save(user);
        // If I want to do more about the new user, I can get it here
        // User newUser = userRepository.save(user);
        return new RegisterResult(RegisterStatus.SUCCESS);
    }

    public LoginResult loginUser(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
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
                jwtSecret, 600000L);
        String refreshToken = jwtUtils.generateToken(userId, roleIds,
                jwtSecretRefresh, 3600000L);
        // TODO add token to redis
        return new LoginResult("Login successful", LoginStatus.SUCCESS, token, refreshToken);
    }

    public void logoutUser() {
        // Logic to log out a user
    }

    public RefreshResult refreshToken(String token, String refreshToken) {
        JwtData refreshData = jwtUtils.verifyToken(refreshToken, jwtSecretRefresh);
        String newToken = jwtUtils.generateToken(refreshData.getUserId(), refreshData.getRoleIds(), jwtSecret, 60000L);
        return new RefreshResult(RefreshStatus.SUCCESS, newToken, refreshToken);
    }
}