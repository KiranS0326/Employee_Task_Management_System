package com.etms.backend.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.LeaveRequest;
import com.etms.backend.service.LeaveService;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    private final LeaveService leaveService;

    LeaveController(LeaveService leaveService) {
        this.leaveService = leaveService;
    }

    @GetMapping
    public List<LeaveRequest> getAll() { return leaveService.getAll(); }

    @GetMapping("/employee/{empId}")
    public List<LeaveRequest> getByEmployee(@PathVariable Integer empId) {
        return leaveService.getByEmployee(empId);
    }

    @PostMapping
    public LeaveRequest apply(@RequestBody LeaveRequest req) { return leaveService.apply(req); }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public LeaveRequest updateStatus(@PathVariable Integer id, @RequestParam LeaveRequest.LeaveStatus status) {
        return leaveService.updateStatus(id, status);
    }
}