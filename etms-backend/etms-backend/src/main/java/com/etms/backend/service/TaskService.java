package com.etms.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.Task;
import com.etms.backend.repository.TaskRepository;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    
    private final NotificationService notificationService;

    TaskService(TaskRepository taskRepository, NotificationService notificationService) {
        this.taskRepository = taskRepository;
        this.notificationService = notificationService;
    }

    public List<Task> getAll() { 
        return taskRepository.findAll(); 
    }

    public List<Task> getByEmployee(Integer empId) { 
        return taskRepository.findByEmployeeId(empId); 
    }

    public Task create(Task t) {
        Task saved = taskRepository.save(t);
        if (saved.getEmployee() != null && saved.getEmployee().getUser() != null) {
            notificationService.notify(saved.getEmployee().getUser(), "New task assigned: " + saved.getTitle());
        }
        return saved;
    }

    public Task updateStatus(Integer id, Task.TaskStatus status) {
        Task t = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        t.setStatus(status);
        return taskRepository.save(t);
    }

    public void delete(Integer id) { 
        taskRepository.deleteById(id); 
    }
}