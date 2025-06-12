package com.mysbproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication(scanBasePackages = "com.mysbproject")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}