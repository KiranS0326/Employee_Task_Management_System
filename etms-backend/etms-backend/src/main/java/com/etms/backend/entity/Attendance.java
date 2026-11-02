package com.etms.backend.entity;

import java.time.LocalDate;
import java.time.LocalTime;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "attendance")
@Data @NoArgsConstructor @AllArgsConstructor
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "att_seq")
    @SequenceGenerator(name = "att_seq", sequenceName = "attendance_seq", allocationSize = 1)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "attendance_date")
    private LocalDate attendanceDate;
    private LocalTime checkIn;
    private LocalTime checkOut;

    @Enumerated(EnumType.STRING)
    private AttendanceStatus status = AttendanceStatus.PRESENT;

    public enum AttendanceStatus { PRESENT, ABSENT, HALF_DAY, ON_LEAVE }
}