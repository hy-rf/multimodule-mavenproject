package com.backend.viewmodel;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class CurrentUserResponse {
    private String username;
    private List<String> roles;
}