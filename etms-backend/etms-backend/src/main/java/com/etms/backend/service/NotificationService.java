package com.etms.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.Notification;
import com.etms.backend.entity.User;
import com.etms.backend.repository.NotificationRepository;

@Service
public class NotificationService {
    
    private final NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public List<Notification> getForUser(Integer userId) {
        return notificationRepository.findByRecipientIdOrderByCreatedAtDesc(userId);
    }
    
    public long unreadCount(Integer userId) {
        return notificationRepository.countByRecipientIdAndIsReadFalse(userId);
    }
    
    public void notify(User recipient, String message) {
        Notification n = new Notification();
        n.setRecipient(recipient);
        n.setMessage(message);
        notificationRepository.save(n);
    }
    
    public Notification markRead(Integer id) {
        Notification n = notificationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Notification not found"));
        n.setRead(true);
        return notificationRepository.save(n);
    }
}