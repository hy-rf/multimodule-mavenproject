package com.backend.service;

import com.backend.model.Post;
import com.backend.model.User;
import com.backend.repository.PostRepository;
import com.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private UserRepository userRepository;

  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Post createPost(String title, String content, Long userId) {
    Optional<User> userOpt = userRepository.findById(userId);
    if (!userOpt.isPresent()) {
      throw new IllegalArgumentException("User not found with id: " + userId);
    }
    Post post = new Post();
    post.setTitle(title);
    post.setContent(content);
    post.setAuthor(userOpt.get());
    return postRepository.save(post);
  }
}
