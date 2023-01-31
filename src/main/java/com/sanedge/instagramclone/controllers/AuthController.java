package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.instagramclone.dto.request.AuthRequest;
import com.sanedge.instagramclone.dto.request.RegisterRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.service.impl.AuthImplService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    AuthImplService authImplService;

    @PostMapping("/login")
    public ResponseEntity<MessageResponse> login(@Valid @RequestBody AuthRequest authRequest) {
        MessageResponse messageResponse = this.authImplService.loginUser(authRequest);

        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/register")
    public ResponseEntity<MessageResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        MessageResponse messageResponse = this.authImplService.register(registerRequest);

        return ResponseEntity.ok(messageResponse);
    }
}
