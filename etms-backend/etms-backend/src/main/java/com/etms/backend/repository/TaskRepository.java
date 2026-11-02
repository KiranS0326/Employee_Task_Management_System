package com.etms.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.backend.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {
    List<Task> findByEmployeeId(Integer employeeId);
}