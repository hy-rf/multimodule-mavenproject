package com.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.User;
import com.backend.service.UserService;
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
        // TODO: Call User Service
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

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/user/{id}")
    public User getUserById(@PathVariable Integer id) {
        // Logic to retrieve user by ID

        return userService.getUserById(id.longValue());
    }

    /**
     * Updates a user based on the provided UpdateUserRequest.
     * Only accessible by users with the 'admin' role.
     *
     * @param updateUserRequest
     * @return
     */
    @PreAuthorize("hasRole('admin')")
    @PutMapping("/user")
    public ResponseEntity<UpdateUserResult> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {
        // TODO: check result of updateUser Service call
        userService.updateUser(updateUserRequest);
        UpdateUserResult result = new UpdateUserResult("success");
        return new ResponseEntity<>(result, HttpStatusCode.valueOf(200));
    }
}
