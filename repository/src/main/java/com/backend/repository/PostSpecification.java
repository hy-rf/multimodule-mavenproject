package com.backend.repository;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.backend.model.Post;
import com.backend.model.User;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;

public class PostSpecification {

    public static Specification<Post> hasTitleOrContentLike(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isEmpty()) return null;
            String pattern = "%" + keyword.toLowerCase() + "%";
            Predicate titlePredicate = cb.like(cb.lower(root.get("title")), pattern);
            Predicate contentPredicate = cb.like(cb.lower(root.get("content")), pattern);
            return cb.or(titlePredicate, contentPredicate);
        };
    }

    public static Specification<Post> hasAuthorNameLike(String authorName) {
        return (root, query, cb) -> {
            if (authorName == null || authorName.isEmpty()) return null;
            Join<Post, User> author = root.join("author", JoinType.INNER);
            return cb.like(cb.lower(author.get("username")), "%" + authorName.toLowerCase() + "%");
        };
    }

    public static Specification<Post> createdAfter(LocalDateTime from) {
        return (root, query, cb) -> {
            if (from == null) return null;
            return cb.greaterThanOrEqualTo(root.get("createdAt"), from);
        };
    }

    public static Specification<Post> createdBefore(LocalDateTime to) {
        return (root, query, cb) -> {
            if (to == null) return null;
            return cb.lessThanOrEqualTo(root.get("createdAt"), to);
        };
    }
}