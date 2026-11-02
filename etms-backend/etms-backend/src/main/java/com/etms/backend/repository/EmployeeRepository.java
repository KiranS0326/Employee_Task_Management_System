package com.etms.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.backend.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {}