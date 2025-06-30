package com.backend.service;

import com.backend.model.User;
import com.backend.repository.RoleRepository;
import com.backend.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
public class AuthServiceTest {
    @InjectMocks
    private AuthService authService = new AuthService();

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @BeforeEach
    public void setup() {
        log.info("Start a test...");
    }

    @Test
    public void testFindUserByUsername() {
        User user = new User();
        user.setUsername("test");
        user.setPasswordHash("z7UbiO1n6KyBeS0O6duzfA==:Z8/P5c1jCqjKjjB92kh/AS27jDE4nn0hnmfPkFuZC+g=");
        when(userRepository.findByUsername("test")).thenReturn(Optional.of(user));
        Optional<User> userOpt = userRepository.findByUsername("test");
        assert (userOpt.isPresent());
        assert ("test".equals(userOpt.get().getUsername()));
    }

    @Test
    public void testLogoutUser() {
        authService.logoutUser();
    }
}
