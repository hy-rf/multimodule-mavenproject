package com.backend.controller;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.backend.model.Post;
import com.backend.model.Reply;
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

    @GetMapping("/my-posts")
    @PreAuthorize("isAuthenticated()")
    public List<Post> getPostsByUser(HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId(); // This should be replaced with actual user ID retrieval logic
        return postService.getPostsByUser(userId);
    }

    @GetMapping("/post/{id}")
    public Post getPostById(@PathVariable Long id, HttpServletResponse response) {
        Post post = postService.getPostById(id);
        if (post == null) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null; // or throw an exception
        }
        return post;
    }

    @GetMapping("/posts/search")
    public List<Post> getPosts(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String authorName,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdAfter,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime createdBefore,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String order,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size) {
        return postService.getPosts(keyword, authorName, createdAfter, createdBefore, sortBy, order, page, size);
    }

    @PostMapping("reply")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> replyToPost(@Valid @RequestBody CreateReplyRequest replyRequest,
            HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        Long userId = userDetails.getId();
        String content = replyRequest.getContent();
        Optional<Long> postId = replyRequest.getPostId();
        Optional<Long> parentReplyId = replyRequest.getParentReplyId();
        String result = postService.createReply(userId, content, postId, parentReplyId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/post/{id}/replies")
    public List<Reply> getRepliesByPostId(@PathVariable Long id, HttpServletResponse response) {
        List<Reply> replies = postService.getRepliesByPostId(id);
        if (replies == null || replies.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null; // or throw an exception
        }
        return replies;
    }

    @GetMapping("/reply/{id}/replies")
    public List<Reply> getRepliesByParentReplyId(@PathVariable Long id, HttpServletResponse response) {
        List<Reply> reply = postService.getRepliesByParentReplyId(id);
        if (reply == null || reply.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return null; // or throw an exception
        }
        return reply;
    }
}
