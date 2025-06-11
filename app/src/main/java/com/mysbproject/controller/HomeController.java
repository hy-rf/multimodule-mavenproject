package com.mysbproject.controller;

import com.mysbproject.service.MyService;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Cookie;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    MyService myService;

    @GetMapping("/")
    public String home(HttpServletResponse response) {
        Cookie cookie = new Cookie("myCookie", "cookieValue");
        cookie.setPath("/");
        response.addCookie(cookie);
        return myService.message();
    }
}
