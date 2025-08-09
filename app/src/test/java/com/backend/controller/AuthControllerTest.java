package com.backend.controller;

import com.backend.common.JwtUtils;
import com.backend.dto.auth.LoginResult;
import com.backend.dto.auth.LoginStatus;
import com.backend.dto.auth.RefreshResult;
import com.backend.dto.auth.RefreshStatus;
import com.backend.dto.auth.RegisterResult;
import com.backend.dto.auth.RegisterStatus;
import com.backend.service.AuthService;
import com.backend.service.LoginRateLimiterService;
import com.backend.viewmodel.LoginRequest;
import com.backend.viewmodel.RegisterRequest;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class AuthControllerTest {
    @Mock
    private JwtUtils jwtUtils;

    @Mock
    private AuthService authService;

    @Mock
    private LoginRateLimiterService loginRateLimiterService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @InjectMocks
    private AuthController authController;

    private AutoCloseable mocks;

    @BeforeEach
    void setUp() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        if (mocks != null) {
            mocks.close();
        }
    }

    @Test
    void testRegisterSuccess() {
        RegisterRequest req = new RegisterRequest();
        req.username = "user";
        req.password = "Password123";

        when(authService.registerUser(any(), any())).thenReturn(new RegisterResult(RegisterStatus.SUCCESS));
        String result = authController.signup(req, session, response);

        assertEquals("User registered successfully", result);
    }

    @Test
    void testRegisterUsernameTaken() {
        RegisterRequest req = new RegisterRequest();
        req.username = "user";
        req.password = "Password123";

        when(authService.registerUser(any(), any())).thenReturn(new RegisterResult(RegisterStatus.USERNAME_TAKEN));
        String result = authController.signup(req, session, response);

        assertEquals("Username is already taken", result);
    }

    @Test
    void testLoginSuccess() {
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("pass");

        when(loginRateLimiterService.isAllowed(any())).thenReturn(true);
        when(authService.loginUser(any(), any())).thenReturn(new LoginResult("Success", LoginStatus.SUCCESS, "jwt-token", "refresh-token"));

        ResponseEntity<String> result = authController.login(req, session, response);

        assertEquals(ResponseEntity.ok().body("Login successful"), result);
        verify(response, times(2)).addCookie(any());
    }

    @Test
    void testLoginRateLimitExceeded() {
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("pass");

        when(loginRateLimiterService.isAllowed(any())).thenReturn(false);

        ResponseEntity<String> result = authController.login(req, session, response);
        //assertEquals("Too many login attempts. Please try again later.", result);
    }

    @Test
    void testLoginInvalidPassword() {
        LoginRequest req = new LoginRequest();
        req.setUsername("user");
        req.setPassword("wrong_password");

        when(loginRateLimiterService.isAllowed(any())).thenReturn(true);
        when(authService.loginUser(any(), any())).thenReturn(new LoginResult("", LoginStatus.INVALID_PASSWORD, null, null));

        ResponseEntity<String> result = authController.login(req, session, response);
        assertEquals(ResponseEntity.badRequest().body("F"), result);
    }

    @Test
    void testRefreshSuccess() {
        when(jwtUtils.resolveToken(any())).thenReturn("token");
        when(jwtUtils.resolveRefreshToken(any())).thenReturn("refresh");
        when(authService.refreshToken(any(), any())).thenReturn(
                new RefreshResult(RefreshStatus.SUCCESS, "newToken", "newRefresh")
        );

        String result = authController.refresh(request, response);
        assertEquals("Token refreshed successfully", result);
        verify(response, times(2)).addCookie(any());
    }

    @Test
    void testRefreshFail() {
        when(jwtUtils.resolveToken(any())).thenReturn("token");
        when(jwtUtils.resolveRefreshToken(any())).thenReturn("refresh");
        when(authService.refreshToken(any(), any())).thenReturn(
                new RefreshResult(RefreshStatus.FAIL, null, null)
        );

        String result = authController.refresh(request, response);
        assertEquals("Fail", result);
    }

    @Test
    void testLogout() {
        // String result = authController.logout();
        // assertEquals("User logged out successfully", result);
    }
}

