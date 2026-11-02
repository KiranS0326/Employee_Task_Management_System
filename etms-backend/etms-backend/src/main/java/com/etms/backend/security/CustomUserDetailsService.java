package com.etms.backend.security;

import java.util.List;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.etms.backend.entity.User;
import com.etms.backend.repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println(">>> [DEBUG] Attempting login for username: " + username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    System.out.println(">>> [DEBUG] User NOT found in database: " + username);
                    return new UsernameNotFoundException("User not found with username: " + username);
                });

        System.out.println(">>> [DEBUG] User found in DB: " + user.getUsername());
        System.out.println(">>> [DEBUG] Password hash from DB: " + user.getPassword());

        // Safely extract role name to prevent NullPointerException
        String roleName = "ROLE_USER";
        if (user.getRole() != null && user.getRole().getName() != null) {
            roleName = user.getRole().getName();
        }

        // Ensure role has ROLE_ prefix expected by Spring Security
        if (!roleName.startsWith("ROLE_")) {
            roleName = "ROLE_" + roleName;
        }

        System.out.println(">>> [DEBUG] Assigned Authority: " + roleName);

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                List.of(new SimpleGrantedAuthority(roleName))
        );
    }
}