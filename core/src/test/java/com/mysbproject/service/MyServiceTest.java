package com.mysbproject.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:mysql://localhost:3306/mmdb",
        "spring.datasource.username=mmdbuser",
        "spring.datasource.password=00000000",
        "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
})
public class MyServiceTest {

    @Autowired
    private MyService myService;

    @Test
    public void contextLoads() {
        assertThat(myService.message()).isNotNull();
    }

    @SpringBootApplication(scanBasePackages = "com.mysbproject")
    static class TestConfiguration {
    }

}