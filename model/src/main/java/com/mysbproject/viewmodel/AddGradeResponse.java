package com.mysbproject.viewmodel;

import lombok.Data;

@Data
public class AddGradeResponse {
  private String message;

  public AddGradeResponse(String message) {
    this.message = message;
  }
}
