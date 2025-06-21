package com.backend.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.backend.common.JwtUtils;
import com.backend.dto.JwtData;
import com.backend.service.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/// * AuthorizeFilter is a filter that intercepts HTTP requests to check for JWT tokens.
/* It extracts the token from the request, verifies it, and processes the JWT data.
* This filter is executed once per request.
*/
@Component
@Slf4j
public class AuthorizationFilter extends OncePerRequestFilter {

  @Value("${jwt.secret}")
  private String jwtSecret;

  @Autowired
  private JwtUtils jwtUtils;

  @Autowired
  private CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    String token = jwtUtils.resolveToken(request);
    JwtData jwtData = jwtUtils.verifyToken(token, jwtSecret);
    // Handle the case where JWT data have values which means user is logged in
    if (jwtData == null) {
      log.info("JWT Data is null, user is not logged in.");
    } else {
      UserDetails userDetails = customUserDetailsService.loadUserById(jwtData.getUserId());
      UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
          null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);
    }
    filterChain.doFilter(request, response);
  }
}
