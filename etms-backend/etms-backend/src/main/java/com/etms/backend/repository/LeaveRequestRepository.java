package com.etms.backend.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.backend.entity.LeaveRequest;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Integer> {
    List<LeaveRequest> findByEmployeeId(Integer employeeId);
    List<LeaveRequest> findByStatus(LeaveRequest.LeaveStatus status);
}