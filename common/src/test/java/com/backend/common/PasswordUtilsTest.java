package com.backend.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordUtilsTest {

  @Test
  void testHashAndVerifyPassword_Success() {
    String password = "StrongPassword!123";
    String hash = PasswordUtils.hashPassword(password);
    System.out.println("Generated Hash: " + hash);
    assertNotNull(hash, "Hash should not be null");
    assertTrue(PasswordUtils.verifyPassword(password, hash));
  }

  @Test
  void testVerifyPassword_Failure() {
    String password = "CorrectPassword";
    String wrongPassword = "WrongPassword";
    String hash = PasswordUtils.hashPassword(password);
    assertFalse(PasswordUtils.verifyPassword(wrongPassword, hash));
  }

  @Test
  void testHashPassword_DifferentHashesForSamePassword() {
    String password = "RepeatablePassword";
    String hash1 = PasswordUtils.hashPassword(password);
    String hash2 = PasswordUtils.hashPassword(password);
    assertNotEquals(hash1, hash2, "Hashes should be different due to random salt");
  }

  @Test
  void testVerifyPassword_InvalidStoredFormat() {
    String password = "AnyPassword";
    String invalidStored = "not_a_valid_hash_format";
    assertFalse(PasswordUtils.verifyPassword(password, invalidStored));
  }
}