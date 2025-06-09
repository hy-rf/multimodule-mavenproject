package com.mysbproject.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class GetDatabase {

    @Autowired
    private DataSource dataSource;

    public void getDriver() {
        System.out.println("GetDatabase.getDriver() called");
    }

    public void printTableNames() {
        try (Connection conn = dataSource.getConnection()) {
            ResultSet rs = conn.getMetaData().getTables(null, null, "%", new String[] { "TABLE" });
            System.out.println("Tables in the database:");
            while (rs.next()) {
                System.out.println(rs.getString("TABLE_NAME"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
