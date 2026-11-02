package com.etms.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.Comment;
import com.etms.backend.service.CommentService;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/task/{taskId}")
    public List<Comment> getByTask(@PathVariable Integer taskId) { return commentService.getByTask(taskId); }

    @PostMapping
    public Comment add(@RequestBody Comment c) { return commentService.add(c); }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        commentService.delete(id);
        return "Deleted";
    }
}