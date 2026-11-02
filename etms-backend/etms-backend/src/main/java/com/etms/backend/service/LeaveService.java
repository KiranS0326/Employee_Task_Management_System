package com.etms.backend.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.LeaveRequest;
import com.etms.backend.repository.LeaveRequestRepository;

@Service
public class LeaveService {
    
    private final LeaveRequestRepository leaveRequestRepository;

    LeaveService(LeaveRequestRepository leaveRequestRepository) {
        this.leaveRequestRepository = leaveRequestRepository;
    }

    public List<LeaveRequest> getAll() { 
        return leaveRequestRepository.findAll(); 
    }
    
    public List<LeaveRequest> getByEmployee(Integer empId) { 
        return leaveRequestRepository.findByEmployeeId(empId); 
    }
    
    public LeaveRequest apply(LeaveRequest req) { 
        return leaveRequestRepository.save(req); 
    }

    public LeaveRequest updateStatus(Integer id, LeaveRequest.LeaveStatus status) {
        LeaveRequest req = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
        req.setStatus(status);
        return leaveRequestRepository.save(req);
    }
}