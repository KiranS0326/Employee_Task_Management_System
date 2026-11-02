package com.etms.backend.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import com.etms.backend.entity.Attendance;
import com.etms.backend.service.AttendanceService;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {

    private final AttendanceService attendanceService;

    // Added @Autowired for explicit constructor injection
    AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @GetMapping
    public List<Attendance> getAll() { 
        return attendanceService.getAll(); 
    }

    // FIX: Added 'public' access modifier
    @GetMapping("/employee/{empId}")
    public List<Attendance> getByEmployee(@PathVariable Integer empId) {
        return attendanceService.getByEmployee(empId);
    }

    // FIX: Added 'public' access modifier
    @PostMapping("/checkin/{empId}")
    public Attendance checkIn(@PathVariable Integer empId) {
        return attendanceService.checkIn(empId);
    }

    // FIX: Added 'public' access modifier
    @PutMapping("/checkout/{attendanceId}")
    public Attendance checkOut(@PathVariable Integer attendanceId) {
        return attendanceService.checkOut(attendanceId);
    }
}