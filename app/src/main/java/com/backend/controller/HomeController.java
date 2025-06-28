package com.backend.controller;

import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HomeController {

    @Value("${home.title}")
    private String title;

    @GetMapping("/home")
    @PreAuthorize("permitAll()")
    public String home(HttpServletResponse response) {
        log.info("Home endpoint accessed");
        return title;
    }

    @GetMapping("/")
    @PreAuthorize("permitAll()")
    public void index(HttpServletResponse response) {
        try {
            response.sendRedirect("/swagger-ui.html");
        } catch (Exception e) {
            log.error("Redirect failed", e);
        }
    }
}
