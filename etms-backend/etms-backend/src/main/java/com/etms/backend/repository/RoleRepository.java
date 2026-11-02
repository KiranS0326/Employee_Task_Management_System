package com.etms.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.etms.backend.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Role findByName(String name);
}