package com.etms.backend.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "comments")
@Data @NoArgsConstructor @AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_seq")
    @SequenceGenerator(name = "comment_seq", sequenceName = "comments_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "task_id")
    private Task task;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;

    @Column(length = 1000)
    private String content;

    private LocalDateTime createdAt = LocalDateTime.now();
}