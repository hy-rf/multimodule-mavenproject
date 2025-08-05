package com.backend.service;

import com.backend.model.User;
import com.backend.model.Role;
import com.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

        @Autowired
        private UserRepository userRepository;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userRepository.findByUsername(username)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));

                return org.springframework.security.core.userdetails.User.builder()
                                .username(user.getUsername())
                                .password(user.getPasswordHash())
                                .authorities(
                                                user.getRoles().stream()
                                                                .map(Role::getName)
                                                                .map(roleName -> new SimpleGrantedAuthority(
                                                                                "ROLE_" + roleName))
                                                                .collect(Collectors.toList()))
                                .accountLocked(Boolean.FALSE.equals(user.getIsActive()))
                                .disabled(Boolean.FALSE.equals(user.getIsActive()))
                                .build();
        }

        public UserDetails loadUserById(Long id) {
                User user = userRepository.findById(id)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + id));
                return new CustomUserDetails(user);
        }
}