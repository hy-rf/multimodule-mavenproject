package com.mysbproject.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
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
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) throws ServletException {
    String path = request.getServletPath();
    System.out.println(path);
    return path.equals("/") || path.equals("/login") || path.equals("/register");
  }

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtUtils.resolveToken(request);
    JwtData jwtData = jwtUtils.verifyToken(token, jwtSecret);
    // Handle the case where JWT data have values which means user is logged in
    if (jwtData == null) {
      System.out.println("JWT Data is null, user is not logged in.");
    } else {
      // Set authentication in the security context
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwtData.getUserId(),
          null, Collections.emptyList());
      SecurityContextHolder.getContext().setAuthentication(authentication);

    }
    filterChain.doFilter(request, response);
  }

}
