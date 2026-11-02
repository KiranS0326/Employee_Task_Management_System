package com.etms.backend.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.Attendance;
import com.etms.backend.entity.Employee;
import com.etms.backend.repository.AttendanceRepository;

@Service
public class AttendanceService {
    
    private final AttendanceRepository attendanceRepository;
    
    private final EmployeeService employeeService;

    AttendanceService(AttendanceRepository attendanceRepository, EmployeeService employeeService) {
        this.attendanceRepository = attendanceRepository;
        this.employeeService = employeeService;
    }

    public List<Attendance> getAll() { 
        return attendanceRepository.findAll(); 
    }
    
    public List<Attendance> getByEmployee(Integer empId) { 
        return attendanceRepository.findByEmployeeId(empId); 
    }

    public Attendance checkIn(Integer employeeId) {
        LocalDate today = LocalDate.now();
        
        Attendance existing = attendanceRepository.findByEmployeeIdAndAttendanceDate(employeeId, today).orElse(null);
        if (existing != null) {
            return existing;
        }

        Employee emp = employeeService.getById(employeeId);

        Attendance a = new Attendance();
        a.setEmployee(emp); 
        a.setAttendanceDate(today); 
        a.setCheckIn(LocalTime.now());
        a.setStatus(Attendance.AttendanceStatus.PRESENT);
        
        return attendanceRepository.save(a);
    }

    public Attendance save(Attendance a) { 
        return attendanceRepository.save(a); 
    }

    public Attendance checkOut(Integer attendanceId) {
        Attendance a = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new RuntimeException("Attendance record not found"));
        a.setCheckOut(LocalTime.now()); 
        return attendanceRepository.save(a);
    }
}