package com.backend.service;

import com.backend.model.Post;
import com.backend.model.Reply;
import com.backend.model.User;
import com.backend.repository.PostRepository;
import com.backend.repository.PostSpecification;
import com.backend.repository.ReplyRepository;
import com.backend.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Service
public class PostService {

  @Autowired
  private PostRepository postRepository;

  @Autowired
  private ReplyRepository replyRepository;

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
      String order, int page,
      int size) {
    Specification<Post> spec = Specification.where(PostSpecification.hasTitleOrContentLike(keyword))
        .and(PostSpecification.hasAuthorNameLike(authorName))
        .and(PostSpecification.createdAfter(createdAfter))
        .and(PostSpecification.createdBefore(createdBefore));

    Sort.Direction direction = order.equalsIgnoreCase("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(page - 1, size, Sort.by(direction, sortBy));

    return postRepository.findAll(spec, pageable).getContent();
  }

  public String createReply(Long postId, String content, Long userId, Long parentReplyId) {
    Optional<Post> postOpt = postRepository.findById(postId);
    if (!postOpt.isPresent()) {
      throw new IllegalArgumentException("Post not found with id: " + postId);
    }

    Post post = postOpt.get();
    Reply reply = new Reply();
    reply.setContent(content);
    reply.setPost(post);
    reply.setAuthor(userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId)));
    if (parentReplyId != null) {
      reply.setParentReply(replyRepository.findById(parentReplyId)
          .orElseThrow(() -> new IllegalArgumentException("Parent reply not found with id: " + parentReplyId)));
    } else {
      reply.setParentReply(null);
    }

    post.getReplies().add(reply);
    postRepository.save(post);

    return "Reply created successfully";
  }
}
