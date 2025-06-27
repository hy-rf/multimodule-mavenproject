package com.backend.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

@Configuration
public class DataSourceConfig {
    // TODO move connection properties to application.properties

    @Value("${spring.datasource.url}")
    private String mysqlUrl;

    @Value("${spring.datasource.username}")
    private String mysqlUsername;

    @Value("${spring.datasource.password}")
    private String mysqlPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Try MySQL first
        try {
            DriverManagerDataSource mysqlDataSource = new DriverManagerDataSource();
            mysqlDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
            mysqlDataSource.setUrl(mysqlUrl);
            mysqlDataSource.setUsername(mysqlUsername);
            mysqlDataSource.setPassword(mysqlPassword);
            try (Connection conn = mysqlDataSource.getConnection()) {
                ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
                populator.addScript(new ClassPathResource("schema-mysql.sql"));
                try {
                    populator.execute(mysqlDataSource);
                } catch (Exception e) {
                    System.out.println("Failed to initialize SQLite schema: " + e.getMessage());
                }
                return mysqlDataSource;
            }
        } catch (SQLException ex) {
            System.out.println("MySQL unavailable, falling back to SQLite: " + ex.getMessage());
        }

        // Fallback to postgresSQL
        try {
            DriverManagerDataSource postgresDataSource = new DriverManagerDataSource();
            postgresDataSource.setDriverClassName("org.postgresql.Driver");
            postgresDataSource.setUrl("jdbc:postgresql://localhost:5432/mmdb");
            postgresDataSource.setUsername("postgres");
            postgresDataSource.setPassword("");
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
        } catch (SQLException ex) {
            System.out.println("PostgresSQL unavailable, falling back to SQLite: " + ex.getMessage());
        }

        // Fallback to SQLite
        DriverManagerDataSource sqliteDataSource = new DriverManagerDataSource();
        sqliteDataSource.setDriverClassName("org.sqlite.JDBC");
        sqliteDataSource.setUrl("jdbc:sqlite:./fallback.db");

        // Run SQLite schema script
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScript(new ClassPathResource("schema-sqlite.sql"));
        try {
            populator.execute(sqliteDataSource);
        } catch (Exception e) {
            System.out.println("Failed to initialize SQLite schema: " + e.getMessage());
        }
        // Set Hibernate dialect for SQLite
        System.setProperty("spring.jpa.properties.hibernate.dialect", "org.hibernate.community.dialect.SQLiteDialect");

        return sqliteDataSource;
    }
}
