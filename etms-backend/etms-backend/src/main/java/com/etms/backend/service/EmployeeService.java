package com.etms.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.Employee;
import com.etms.backend.repository.EmployeeRepository;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAll() { 
        return employeeRepository.findAll(); 
    }
    
    public Employee getById(Integer id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }
    
    public Employee create(Employee e) { 
        return employeeRepository.save(e); 
    }
    
    public Employee update(Integer id, Employee updated) {
        Employee e = getById(id);
        e.setFullName(updated.getFullName());
        e.setEmail(updated.getEmail());
        e.setPhone(updated.getPhone());
        e.setDesignation(updated.getDesignation());
        e.setDepartment(updated.getDepartment());
        return employeeRepository.save(e);
    }
    
    public void delete(Integer id) { 
        employeeRepository.deleteById(id); 
    }
}