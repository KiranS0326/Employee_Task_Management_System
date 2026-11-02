package com.etms.backend.controller;

import java.util.List;
// import org.springframework.security.access.prepost.PreAuthorize; // Temporarily disabled
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.Employee;
import com.etms.backend.service.EmployeeService;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<Employee> getAll() { return employeeService.getAll(); }

    // FIX: Added ("id")
    @GetMapping("/{id}")
    public Employee getById(@PathVariable("id") Integer id) { return employeeService.getById(id); }

    @PostMapping
    // @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')") // Temporarily disabled
    public Employee create(@RequestBody Employee e) { return employeeService.create(e); }

    // FIX: Added ("id")
    @PutMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')") // Temporarily disabled
    public Employee update(@PathVariable("id") Integer id, @RequestBody Employee e) {
        return employeeService.update(id, e);
    }

    // FIX: Added ("id")
    @DeleteMapping("/{id}")
    // @PreAuthorize("hasRole('ADMIN')") // Temporarily disabled
    public String delete(@PathVariable("id") Integer id) {
        employeeService.delete(id);
        return "Deleted successfully";
    }
}