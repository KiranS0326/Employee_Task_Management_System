package com.etms.backend.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "leave_requests")
@Data @NoArgsConstructor @AllArgsConstructor
public class LeaveRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leave_seq")
    @SequenceGenerator(name = "leave_seq", sequenceName = "leave_requests_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    private LocalDate startDate;
    private LocalDate endDate;
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    private LocalDate appliedDate = LocalDate.now();

    public enum LeaveType { SICK, CASUAL, VACATION, EMERGENCY }
    public enum LeaveStatus { PENDING, APPROVED, REJECTED }
}