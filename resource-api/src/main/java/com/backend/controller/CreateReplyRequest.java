package com.backend.controller;

import java.util.Optional;

import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateReplyRequest {
  private Optional<Long> postId = Optional.empty();
  private Optional<Long> parentReplyId = Optional.empty();

  @NotBlank
  private String content;

  @AssertTrue(message = "Either postId or parentReplyId must be provided, but not both")
  public boolean isExactlyOneTarget() {
    boolean hasPost = postId != null && postId.isPresent();
    boolean hasParent = parentReplyId != null && parentReplyId.isPresent();
    return hasPost ^ hasParent;
  }
}
