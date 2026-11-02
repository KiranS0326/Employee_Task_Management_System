package com.etms.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.backend.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Integer> {}