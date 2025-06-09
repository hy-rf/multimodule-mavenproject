package com.mysbproject.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(properties = {
    "spring.datasource.url=jdbc:mysql://localhost:3306/mmdb",
    "spring.datasource.username=mmdbuser",
    "spring.datasource.password=00000000",
    "spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver"
})
class GetDatabaseTest {

  @Autowired
  private GetDatabase getDatabase;

  @Test
  void printTableNames_shouldPrintAtLeastOneTable() {
    // Capture system output
    ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    PrintStream originalOut = System.out;
    System.setOut(new PrintStream(outContent));

    getDatabase.printTableNames();

    System.setOut(originalOut);
    String output = outContent.toString();

    // Assert that output contains at least one table name
    assertTrue(output.contains("Tables in the database:"));
    // Optionally, check for at least one table name (assuming you have at least one
    // table)
    assertTrue(output.lines().count() > 1, "Should print at least one table name");
  }

  @SpringBootApplication(scanBasePackages = "com.mysbproject")
  static class TestConfiguration {
  }
}