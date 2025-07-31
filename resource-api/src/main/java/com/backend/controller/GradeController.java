package com.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class GradeController {

    @GetMapping("/grade")
    @PreAuthorize("isAuthenticated()")
    public String home(HttpServletResponse response) {
        log.info("Home endpoint accessed");
        return "Welcome to the Grade Management System";
    }
}
