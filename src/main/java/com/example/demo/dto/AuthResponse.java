package com.example.demo.dto;
public class AuthResponse {

    private String token;
    public AuthResponse(String token, String uid) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


}
