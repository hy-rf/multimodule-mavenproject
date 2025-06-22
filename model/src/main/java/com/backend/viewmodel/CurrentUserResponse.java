package com.backend.viewmodel;

import java.util.List;

public class CurrentUserResponse {
    private String username;
    private List<String> roles;

    public CurrentUserResponse(String username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }
}