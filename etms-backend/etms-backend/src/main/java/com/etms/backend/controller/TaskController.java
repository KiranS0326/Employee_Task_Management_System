package com.etms.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.Task;
import com.etms.backend.service.TaskService;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public List<Task> getAll() { return taskService.getAll(); }

    @GetMapping("/employee/{empId}")
    public List<Task> getByEmployee(@PathVariable Integer empId) { return taskService.getByEmployee(empId); }

    @PostMapping
    public Task create(@RequestBody Task t) { return taskService.create(t); }

    @PutMapping("/{id}/status")
    public Task updateStatus(@PathVariable Integer id, @RequestParam Task.TaskStatus status) {
        return taskService.updateStatus(id, status);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable Integer id) {
        taskService.delete(id);
        return "Deleted successfully";
    }
}