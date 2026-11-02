package com.etms.backend.entity;

import java.time.LocalDate;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tasks")
@Data @NoArgsConstructor @AllArgsConstructor
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_seq")
	@SequenceGenerator(name = "task_seq", sequenceName = "tasks_seq", allocationSize = 1)
	private Integer id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TaskStatus status = TaskStatus.PENDING;

    private LocalDate dueDate;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public enum TaskStatus { PENDING, IN_PROGRESS, DONE }
}