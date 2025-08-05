package com.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.Post;
import com.backend.service.CustomUserDetails;
import com.backend.service.PostService;

@Slf4j
@RestController
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/posts")
    // @PreAuthorize("isAuthenticated()")
    public List<Post> gePosts(HttpServletResponse response) {
        return postService.getAllPosts();
    }

    @PostMapping("/post")
    @PreAuthorize("isAuthenticated()")
    public String home(@RequestBody CreatePostRequest createPostRequest, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getId(); // This should be replaced with actual user ID retrieval logic
        postService.createPost(createPostRequest.getTitle(), createPostRequest.getContent(), userId);
        return "Successfully created post with title: " + createPostRequest.getTitle();
    }
}
