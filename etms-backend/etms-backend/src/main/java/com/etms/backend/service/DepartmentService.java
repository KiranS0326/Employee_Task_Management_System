package com.etms.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.Department;
import com.etms.backend.repository.DepartmentRepository;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAll() { 
        return departmentRepository.findAll(); 
    }
    
    public Department getById(Integer id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Department not found"));
    }
    
    public Department create(Department d) { 
        return departmentRepository.save(d); 
    }
    
    public Department update(Integer id, Department updated) {
        Department d = getById(id);
        d.setName(updated.getName());
        return departmentRepository.save(d);
    }
    
    public void delete(Integer id) { 
        departmentRepository.deleteById(id); 
    }
}