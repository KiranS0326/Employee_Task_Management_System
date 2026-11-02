package com.etms.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_seq")
    @SequenceGenerator(name = "role_seq", sequenceName = "roles_seq", allocationSize = 1)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role() {}

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}