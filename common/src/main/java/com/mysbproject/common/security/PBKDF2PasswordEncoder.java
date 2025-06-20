package com.mysbproject.common.security;

import org.springframework.security.crypto.password.PasswordEncoder;

import com.mysbproject.common.PasswordUtils;
import org.springframework.stereotype.Component;

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
