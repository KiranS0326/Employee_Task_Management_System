package com.etms.backend.repository;

import com.etms.backend.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.Optional;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    List<Attendance> findByEmployeeId(Integer employeeId);
    
    Optional<Attendance> findByEmployeeIdAndAttendanceDate(Integer employeeId, LocalDate attendanceDate);
}