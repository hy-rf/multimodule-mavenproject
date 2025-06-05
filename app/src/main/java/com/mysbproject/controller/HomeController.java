package com.mysbproject.controller;

import com.mysbproject.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

     @Autowired
     MyService myService;

     @GetMapping("/")
     public String home() {
         return myService.message();
     }
}
