package com.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backend.service.MigrationService;

@RestController
@RequestMapping("/admin")
public class AdminController {

  @Autowired
  private MigrationService migrationService;

  @GetMapping
  public String migrate(){
    return migrationService.checkAndAddDataIfUserExists();
  }
  
}
