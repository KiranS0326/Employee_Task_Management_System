package com.etms.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.backend.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    List<Comment> findByTaskIdOrderByCreatedAtAsc(Integer taskId);
}