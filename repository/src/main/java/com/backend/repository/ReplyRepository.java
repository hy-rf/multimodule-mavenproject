package com.backend.repository;

import com.backend.model.Reply;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    List<Reply> findByPostId(Long postId);
    List<Reply> findByParentReplyId(Long parentReplyId);
}