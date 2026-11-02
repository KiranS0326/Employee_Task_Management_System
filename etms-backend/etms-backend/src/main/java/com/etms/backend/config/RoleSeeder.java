package com.etms.backend.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.etms.backend.entity.Role;
import com.etms.backend.repository.RoleRepository;

@Component
public class RoleSeeder implements CommandLineRunner {

    private final RoleRepository roleRepository;

    public RoleSeeder(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if ROLE_USER exists, if not, create it
        if (roleRepository.findByName("ROLE_USER") == null) {
            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);
            System.out.println(">>> Seeded ROLE_USER into database.");
        }

        // Check if ROLE_ADMIN exists, if not, create it
        if (roleRepository.findByName("ROLE_ADMIN") == null) {
            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);
            System.out.println(">>> Seeded ROLE_ADMIN into database.");
        }
    }
}