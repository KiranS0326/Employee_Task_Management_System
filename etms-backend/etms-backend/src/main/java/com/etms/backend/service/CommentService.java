package com.etms.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.Comment;
import com.etms.backend.repository.CommentRepository;

@Service
public class CommentService {
    private final CommentRepository commentRepository;

    CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public List<Comment> getByTask(Integer taskId) {
        return commentRepository.findByTaskIdOrderByCreatedAtAsc(taskId);
    }
    public Comment add(Comment c) { return commentRepository.save(c); }
    public void delete(Integer id) { commentRepository.deleteById(id); }
}