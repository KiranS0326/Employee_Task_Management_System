package com.etms.backend.controller;

import java.util.List;
// import org.springframework.security.access.prepost.PreAuthorize; // Temporarily disabled
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.Department;
import com.etms.backend.service.DepartmentService;

@RestController
@RequestMapping("/api/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAll() { return departmentService.getAll(); }

    // FIX: Added ("id")
    @GetMapping("/{id}")
    public Department getById(@PathVariable("id") Integer id) { return departmentService.getById(id); }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN')") // Temporarily disabled so you can add departments
    public Department create(@RequestBody Department d) { return departmentService.create(d); }

    // FIX: Added ("id")
    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Temporarily disabled
    public Department update(@PathVariable("id") Integer id, @RequestBody Department d) {
        return departmentService.update(id, d);
    }

    // FIX: Added ("id")
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Temporarily disabled
    public String delete(@PathVariable("id") Integer id) {
        departmentService.delete(id);
        return "Deleted successfully";
    }
}