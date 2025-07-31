package com.backend;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Slf4j
@SpringBootApplication(scanBasePackages= {"com.backend"})
@EnableJpaRepositories(basePackages = "com.backend.repository")
public class Application {

    @PostConstruct
    public void startupApplication() {
        log.info("Start application");
    }

    @PreDestroy
    public void shutdownApplication() {
        log.info("Shutdown application");
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}