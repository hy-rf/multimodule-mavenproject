package com.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Configuration
public class DataSourceConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Bean
    @Primary
    public DataSource dataSource() throws SQLException {
        DriverManagerDataSource postgresDataSource = new DriverManagerDataSource();
        postgresDataSource.setDriverClassName(driverClassName);
        postgresDataSource.setUrl(url);
        postgresDataSource.setUsername(username);
        postgresDataSource.setPassword(password);
        try (Connection conn = postgresDataSource.getConnection()) {
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
            populator.addScript(new ClassPathResource("schema-postgres.sql"));
            try {
                populator.execute(postgresDataSource);
            } catch (Exception e) {
                System.out.println("Failed to initialize SQLite schema: " + e.getMessage());
            }
            return postgresDataSource;
        }

//        // Fallback to SQLite
//        DriverManagerDataSource sqliteDataSource = new DriverManagerDataSource();
//        sqliteDataSource.setDriverClassName("org.sqlite.JDBC");
//        sqliteDataSource.setUrl("jdbc:sqlite:./fallback.db");
//
//        // Run SQLite schema script
//        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
//        populator.addScript(new ClassPathResource("schema-sqlite.sql"));
//        try {
//            populator.execute(sqliteDataSource);
//        } catch (Exception e) {
//            System.out.println("Failed to initialize SQLite schema: " + e.getMessage());
//        }
//        // Set Hibernate dialect for SQLite
//        System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");
//
//        return sqliteDataSource;
    }
}
