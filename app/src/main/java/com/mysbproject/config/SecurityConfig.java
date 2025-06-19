package com.mysbproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.mysbproject.security.AuthorizeFilter;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

  @Autowired
  private AuthorizeFilter authorizeFilter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .formLogin(form -> form.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth
            // .requestMatchers("/", "/login", "/register", "/refresh", "/home").permitAll()
            .anyRequest().permitAll());
    http.addFilterBefore(authorizeFilter,
        UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
