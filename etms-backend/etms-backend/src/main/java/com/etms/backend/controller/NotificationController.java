package com.etms.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.Notification;
import com.etms.backend.service.NotificationService;

@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getForUser(@PathVariable Integer userId) { return notificationService.getForUser(userId); }

    @GetMapping("/user/{userId}/unread-count")
    public long unreadCount(@PathVariable Integer userId) { return notificationService.unreadCount(userId); }

    @PutMapping("/{id}/read")
    public Notification markRead(@PathVariable Integer id) { return notificationService.markRead(id); }
}