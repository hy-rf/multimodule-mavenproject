package com.backend.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.backend.common.PasswordUtils;
import org.springframework.stereotype.Component;

/**
 * Not in use
 */
@Component
public class PBKDF2PasswordEncoder implements PasswordEncoder {
  @Override
  public String encode(CharSequence rawPassword) {
    return PasswordUtils.hashPassword(rawPassword.toString());
  }

  @Override
  public boolean matches(CharSequence rawPassword, String encodedPassword) {
    return PasswordUtils.verifyPassword(rawPassword.toString(), encodedPassword);
  }
}
