package com.example.demo.controller;
import com.example.demo.dto.AuthRequest;
import com.example.demo.dto.AuthResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    // Endpoint for login that verifies Firebase token
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request) {
        try {
            // The token is sent from the frontend (React Native) after Firebase login
            String idToken = request.getToken();

            // Verify the ID token using Firebase Admin SDK
            FirebaseToken firebaseToken = FirebaseAuth.getInstance().verifyIdToken(idToken);
            String uid = firebaseToken.getUid(); // Extract user ID from token

            // If verification is successful, return a response
            return ResponseEntity.ok(new AuthResponse("Login successful", uid));
        } catch (Exception e) {
            return ResponseEntity.status(401).body("Invalid Firebase token");
        }
    }
}
