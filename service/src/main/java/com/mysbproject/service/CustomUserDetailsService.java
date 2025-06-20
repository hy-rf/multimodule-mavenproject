package com.mysbproject.service;

import com.mysbproject.model.User;
import com.mysbproject.model.Role;
import com.mysbproject.repository.User.UserDao;
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
        private UserDao userDao;

        @Override
        public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                User user = userDao.findByUsername(username)
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
                User user = userDao.getUserById(id);
                if (user == null)
                        throw new UsernameNotFoundException("User not found: " + id);
                return org.springframework.security.core.userdetails.User.builder()
                                .username(user.getUsername())
                                .password(user.getPasswordHash())
                                .authorities(user.getRoles().stream()
                                                .map(Role::getName)
                                                .map(roleName -> new SimpleGrantedAuthority("ROLE_" + roleName))
                                                .collect(Collectors.toList()))
                                .accountLocked(Boolean.FALSE.equals(user.getIsActive()))
                                .disabled(Boolean.FALSE.equals(user.getIsActive()))
                                .build();
        }
}