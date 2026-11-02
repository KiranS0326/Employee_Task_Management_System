package com.etms.backend.dto;

public class JwtResponse {
    private Integer id;
    private String token;
    private String username;
    private String role;

    public JwtResponse(Integer id, String token, String username, String role) {
        this.id = id;
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters
    public Integer getId() { return id; }
    public String getToken() { return token; }
    public String getUsername() { return username; }
    public String getRole() { return role; }

    // Setters
    public void setId(Integer id) { this.id = id; }
    public void setToken(String token) { this.token = token; }
    public void setUsername(String username) { this.username = username; }
    public void setRole(String role) { this.role = role; }
}