package com.backend.helper;

import jakarta.servlet.http.Cookie;

public class TokenHelper {
  public static Cookie createCookie(String name, String value, int maxAge) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Cookie name must not be null or empty.");
        }
        if (value == null || value.isEmpty()) {
            throw new IllegalArgumentException("Cookie value must not be null or empty.");
        }
        if (maxAge < 0) {
            throw new IllegalArgumentException("Cookie maxAge must not be negative.");
        }
        Cookie cookie = new Cookie(name, value);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        cookie.setAttribute("SameSite", "None");
        cookie.setSecure(true);
        return cookie;
    }
}
