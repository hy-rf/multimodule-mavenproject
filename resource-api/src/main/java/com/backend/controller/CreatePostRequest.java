package com.backend.controller;

import lombok.Data;

@Data
public class CreatePostRequest {
  private String title;
  private String content;
}
