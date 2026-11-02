package com.etms.backend.dto;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TaskDto {
    private Integer id;
    private String title;
    private String description;
    private String status;
    private LocalDate dueDate;
    private Integer employeeId;
}