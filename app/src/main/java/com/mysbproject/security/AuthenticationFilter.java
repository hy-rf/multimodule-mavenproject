package com.mysbproject.security;

import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mysbproject.common.JwtUtils;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private JwtUtils jwtUtils = new JwtUtils();

}
