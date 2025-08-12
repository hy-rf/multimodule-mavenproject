package com.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backend.model.Post;
import com.backend.model.Role;
import com.backend.model.User;
import com.backend.repository.PostRepository;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MigrationService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private RoleRepository roleRepository;

  @Autowired
  private PostRepository postRepository;

  @Transactional
  public String checkAndAddDataIfUserExists() {
    Optional<User> userOpt = userRepository.findById(1L);
    if (userOpt.isPresent()) {
      log.info("User with ID 1 exists.");
      return "User with ID 1 exists.";
    }
    log.warn("User with ID 1 does not exist.");

    // Create a new user
    User newUser = new User();
    newUser.setUsername("string");
    newUser.setPasswordHash("z7UbiO1n6KyBeS0O6duzfA==:Z8/P5c1jCqjKjjB92kh/AS27jDE4nn0hnmfPkFuZC+g=");
    newUser.setIsActive(true);

    // Create roles
    Role userRole = roleRepository.findByName("user").orElseGet(() -> {
      Role role = new Role();
      role.setName("user");
      return roleRepository.save(role);
    });

    Role adminRole = roleRepository.findByName("admin").orElseGet(() -> {
      Role role = new Role();
      role.setName("admin");
      return roleRepository.save(role);
    });

    // Assign roles to the user
    List<Role> roles = new ArrayList<>();
    roles.add(userRole);
    roles.add(adminRole);
    newUser.setRoles(roles);

    // Save the user
    User user = userRepository.save(newUser);

    Post post = new Post();
    post.setTitle("title");
    post.setContent("content");
    post.setAuthor(user);

    postRepository.save(post);

    return "User with ID 1 does not exist.";
  }
}
