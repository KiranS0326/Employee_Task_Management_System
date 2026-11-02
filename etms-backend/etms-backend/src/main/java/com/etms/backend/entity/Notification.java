package com.etms.backend.entity;

import java.time.LocalDateTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "notifications")
@Data @NoArgsConstructor @AllArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notif_seq")
    @SequenceGenerator(name = "notif_seq", sequenceName = "notifications_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User recipient;

    private String message;
    private boolean isRead = false;
    private LocalDateTime createdAt = LocalDateTime.now();
}