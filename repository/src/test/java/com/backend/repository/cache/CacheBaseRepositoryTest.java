package com.backend.repository.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = CacheBaseRepositoryTest.TestConfig.class)
class CacheBaseRepositoryTest {

  @Autowired
  private CacheBaseRepository<String, String> cacheBaseRepository;

  @BeforeEach
  void setUp() {
    // Clean up before each test
    cacheBaseRepository.deleteById("testKey");
  }

  @Test
  void testSaveAndFindById() {
    String key = "testKey";
    String value = "testValue";
    cacheBaseRepository.save(key, value);

    Optional<String> result = cacheBaseRepository.findById(key);
    assertTrue(result.isPresent());
    assertEquals(value, result.get());
  }

  @Test
  void testDeleteById() {
    String key = "testKey";
    String value = "testValue";
    cacheBaseRepository.save(key, value);

    cacheBaseRepository.deleteById(key);
    Optional<String> result = cacheBaseRepository.findById(key);
    assertFalse(result.isPresent());
  }

  @Configuration
  static class TestConfig {
    @Bean
    public CacheBaseRepository<String, String> cacheBaseRepository() {
      return new InMemoryCacheRepository<>();
    }
  }
}