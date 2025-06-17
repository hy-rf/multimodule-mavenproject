package com.mysbproject.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.mysbproject.common.JwtUtils;
import com.mysbproject.dto.JwtData;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/// * AuthorizeFilter is a filter that intercepts HTTP requests to check for JWT tokens.
/* It extracts the token from the request, verifies it, and processes the JWT data.
* This filter is executed once per request.
*/
@Component
public class AuthorizeFilter extends OncePerRequestFilter {

  @Value("${jwt.secret}")
  private String jwtSecret;

  private JwtUtils jwtUtils = new JwtUtils();

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    String path = request.getServletPath();
    return path.equals("/") || path.equals("/login") || path.equals("/register");
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // TODO Auto-generated method stub
    String token = jwtUtils.resolveToken(request);
    JwtData jwtData = jwtUtils.verifyToken(token, jwtSecret);
    // Handle the case where JWT data have values which means user is logged in
    if (jwtData == null) {
      System.out.println("JWT Data is null, user is not logged in.");
      return;
    }
    // Set authentication in the security context
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtData.getUserId(),
        null, Collections.emptyList());
    SecurityContextHolder.getContext().setAuthentication(authentication);
    filterChain.doFilter(request, response);
  }

}
