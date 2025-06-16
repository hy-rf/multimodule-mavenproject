package com.mysbproject.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mysbproject.common.JwtUtils;
import com.mysbproject.dto.JwtData;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/// * JwtAuthenticationFilter is a filter that intercepts HTTP requests to check for JWT tokens.
/* It extracts the token from the request, verifies it, and processes the JWT data.
* This filter is executed once per request.
*/
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private JwtUtils jwtUtils = new JwtUtils();

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String token = jwtUtils.resolveToken(request);
    JwtData jwtData = jwtUtils.verifyToken(token, "your-secret-key");
    System.out.println("JWT Data: " + jwtData);
    filterChain.doFilter(request, response);
  }

}
