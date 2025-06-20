package com.mysbproject.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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

  @Autowired
  private Environment env;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    boolean isDev = java.util.Arrays.asList(env.getActiveProfiles()).contains("dev");
    http
        .csrf(csrf -> {
          if (isDev) {
            csrf.disable();
          }
        })
        .formLogin(form -> form.disable())
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    http.addFilterBefore(authorizeFilter,
        UsernamePasswordAuthenticationFilter.class);
    return http.build();
  }
}
