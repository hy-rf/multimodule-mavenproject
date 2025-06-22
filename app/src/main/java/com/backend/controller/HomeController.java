package com.backend.controller;

import com.backend.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    private static final Logger logger = LogManager.getLogger(UserServiceImpl.class);

    @Value("${home.title}")
    private String title;

    @GetMapping("/home")
    @PreAuthorize("permitAll()")
    public String home(HttpServletResponse response) {
        logger.info("Home endpoint accessed");
        return title;
    }

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public void index(HttpServletResponse response) {
        try {
            response.sendRedirect("/swagger-ui.html");
        } catch (Exception e) {
            logger.error("Redirect failed", e);
        }
    }
}
