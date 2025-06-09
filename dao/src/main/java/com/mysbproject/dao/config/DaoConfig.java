package com.mysbproject.dao.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Configuration
@EnableJpaRepositories(basePackages = "com.mysbproject")
@EntityScan(basePackages = "com.mysbproject.dao")
public class DaoConfig {
}
