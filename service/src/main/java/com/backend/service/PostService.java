package com.backend.service;

import com.backend.model.Post;
import com.backend.model.User;
import com.backend.repository.PostRepository;
import com.backend.repository.PostSpecification;
import com.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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

  public List<Post> getPostsByUser(Long userId) {
    Optional<List<Post>> posts = postRepository.findByAuthorId(userId);
    if (posts.isEmpty() || posts.get().isEmpty()) {
      throw new IllegalArgumentException("No posts found for user with id: " + userId);
    }
    return posts.get();
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id)
        .orElse(null); // or throw an exception if preferred
  }

  public List<Post> getPosts(
            String keyword,
            String authorName,
            LocalDateTime createdAfter,
            LocalDateTime createdBefore,
            String sortBy,
            String order
    ) {
        Specification<Post> spec = Specification.where(PostSpecification.hasTitleOrContentLike(keyword))
                .and(PostSpecification.hasAuthorNameLike(authorName))
                .and(PostSpecification.createdAfter(createdAfter))
                .and(PostSpecification.createdBefore(createdBefore));

        Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return postRepository.findAll(spec, sort);
    }
}
