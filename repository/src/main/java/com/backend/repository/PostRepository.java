package com.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.backend.model.Post;

public interface PostRepository extends JpaRepository<Post, Long> {

  
}
