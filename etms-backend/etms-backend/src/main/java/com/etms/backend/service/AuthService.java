package com.etms.backend.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.etms.backend.dto.JwtResponse;
import com.etms.backend.dto.LoginRequest;
import com.etms.backend.dto.RegisterRequest;
import com.etms.backend.entity.Role;
import com.etms.backend.entity.User;
import com.etms.backend.repository.RoleRepository;
import com.etms.backend.repository.UserRepository;
import com.etms.backend.security.JwtUtil;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder,
                       AuthenticationManager authenticationManager,
                       JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    public String register(RegisterRequest req) {
        if (userRepository.findByUsername(req.getUsername()).isPresent()) {
            throw new RuntimeException("Error: Username is already taken!");
        }

        String inputRole = req.getRoleName();
        if (inputRole != null && !inputRole.startsWith("ROLE_")) {
            inputRole = "ROLE_" + inputRole;
        }
        if (inputRole == null) {
            inputRole = "ROLE_USER";
        }

        // --- THE FIX IS HERE ---
        // Fetch Role using standard null checks instead of Optional methods
        Role role = roleRepository.findByName(inputRole);
        
        // If the requested role isn't found, default to ROLE_USER
        if (role == null) {
            role = roleRepository.findByName("ROLE_USER");
            
            // If even the default role isn't in the database, throw an error
            if (role == null) {
                throw new RuntimeException("Error: Default role not found in database.");
            }
        }
        // -----------------------

        User user = new User();
        user.setUsername(req.getUsername());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setRole(role);

        userRepository.save(user);
        return "User registered successfully!";
    }

    public JwtResponse login(LoginRequest req) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword())
        );

        User user = userRepository.findByUsername(req.getUsername())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String roleName = "ROLE_USER";
        if (user.getRole() != null && user.getRole().getName() != null) {
            roleName = user.getRole().getName();
        }

        String token = jwtUtil.generateToken(user.getUsername(), roleName);

        return new JwtResponse(user.getId(), token, user.getUsername(), roleName);
    }
}