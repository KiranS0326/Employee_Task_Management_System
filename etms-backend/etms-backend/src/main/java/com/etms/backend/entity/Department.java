package com.etms.backend.entity;

import java.util.List;
import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "departments")
@Data @NoArgsConstructor @AllArgsConstructor
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dept_seq")
    @SequenceGenerator(name = "dept_seq", sequenceName = "departments_seq", allocationSize = 1)
    private Integer id;

    private String name;

    @OneToMany(mappedBy = "department")
    @JsonIgnore
    private List<Employee> employees;
}