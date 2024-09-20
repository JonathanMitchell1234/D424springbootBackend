package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
public class AuthRequest {

    @NotBlank
    private String token;  // Firebase ID token

    // Getters and setters
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
