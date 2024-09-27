package com.example.demo;

import com.example.demo.dto.AuthRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class AuthRequestTest {

    private AuthRequest authRequest;

    @BeforeEach
    public void setUp() {
        authRequest = new AuthRequest();
    }

    @Test
    public void getToken_returnsNull_whenTokenIsNotSet() {
        assertNull(authRequest.getToken());
    }

    @Test
    public void getToken_returnsToken_whenTokenIsSet() {
        String token = "testToken";
        authRequest.setToken(token);
        assertEquals(token, authRequest.getToken());
    }

    @Test
    public void setToken_setsToken() {
        String token = "testToken";
        authRequest.setToken(token);
        assertEquals(token, authRequest.getToken());
    }
}