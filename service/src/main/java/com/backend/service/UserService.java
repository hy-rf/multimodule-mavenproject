package com.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.backend.model.Role;
import com.backend.repository.RoleRepository;
import com.backend.viewmodel.UpdateUserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

import com.backend.common.PasswordUtils;
import com.backend.model.User;

@Service
@Slf4j
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    // Implement methods for user management, such as creating, updating, and
    // deleting users.
    // This class will interact with the UserRepository to perform database
    // operations.

    // Example method:
    public void createUser(String username, String password) {
        // Logic to create a new user
    }

    public List<User> getAllUsers() {
        log.info("Fetching all users from the database.");
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).get();
    }

    public void saveExampleUser(String userName, String password) {
        String hash = PasswordUtils.hashPassword(password);
        User exUser = new User();
        exUser.setUsername(userName);
        exUser.setPasswordHash(hash);
        userRepository.save(exUser);
    }

    @Transactional
    public void updateUser(UpdateUserRequest updateUserRequest) {
        Optional<User> userOpt = userRepository.findById(updateUserRequest.getId().longValue());
        if (!userOpt.isPresent()) {
            log.error("User with ID {} not found", updateUserRequest.getId());
            throw new RuntimeException("User not found");
        }
        User user = userOpt.get();
        if (updateUserRequest.getUsername() != null)
            user.setUsername(updateUserRequest.getUsername());
        if (updateUserRequest.getEmail() != null)
            user.setEmail(updateUserRequest.getEmail());
        List<Long> roleIds = new ArrayList<>();

        if (updateUserRequest.getPassword() != null) {
            String hashedPassword = PasswordUtils.hashPassword(updateUserRequest.getPassword());
            user.setPasswordHash(hashedPassword);
        }
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
        if (updateUserRequest.getRoleIds() != null) {
            roleIds = updateUserRequest.getRoleIds();
        }
        if (roleIds.size() > 0) {
            List<Role> newRoles = new ArrayList<>();
            for (Long roleId : roleIds) {
                Role role = roleRepository.getReferenceById(roleId);
                newRoles.add(role);
            }
            user.setRoles(newRoles);
        }
        userRepository.save(user);
    }

    // Additional methods can be added here for user-related operations.
}
