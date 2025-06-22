package com.backend.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ActiveProfiles("test")
@SpringBootTest
class GetDatabaseTest {

  @Autowired
  private GetDatabase getDatabase;

  // @Test
  /*
   * This test MUST run with sql server
   */
  void printTableNames_shouldPrintAtLeastOneTable() {
    try {
      ByteArrayOutputStream outContent = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      System.setOut(new PrintStream(outContent));

      getDatabase.printTableNames();

      System.setOut(originalOut);
      String output = outContent.toString();

      assertTrue(output.contains("Tables in the database:"));

      assertTrue(output.lines().count() > 1, "Should print at least one table name");
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @SpringBootApplication(scanBasePackages = "com.backend")
  @EntityScan(basePackages = "com.backend.model")
  static class TestConfiguration {
  }
}