package com.etms.backend.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "employees")
@Data @NoArgsConstructor @AllArgsConstructor
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "emp_seq")
	@SequenceGenerator(name = "emp_seq", sequenceName = "employees_seq", allocationSize = 1)
	private Integer id;

    private String fullName;
    private String email;
    private String phone;
    private String designation;
    private String profilePhotoUrl;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "employee")
    @JsonIgnore
    private List<Task> tasks;
}