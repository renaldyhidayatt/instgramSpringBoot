package com.sanedge.instagramclone.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sanedge.instagramclone.dto.request.AuthRequest;
import com.sanedge.instagramclone.dto.request.RegisterRequest;
import com.sanedge.instagramclone.dto.response.AuthResponse;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.UserRepository;
import com.sanedge.instagramclone.security.JwtProvider;
import com.sanedge.instagramclone.security.UserDetail;

@Service
public class AuthImplService {
    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtProvider;

    @Autowired
    public AuthImplService(UserRepository userRepository, AuthenticationManager authenticationManager,
            PasswordEncoder passwordEncoder, JwtProvider jwtProvider) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtProvider = jwtProvider;
    }

    public MessageResponse loginUser(AuthRequest authRequest) {
        Authentication authentication = this.authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtProvider.generateAccessToken(authentication);

        long expiresAt = jwtProvider.getjwtExpirationMs();
        Date date = new Date();
        date.setTime(expiresAt);

        UserDetail userDetails = (UserDetail) authentication.getPrincipal();

        AuthResponse authResponse = AuthResponse.builder().authenticationToken(jwt).expiresAt(date.toString())
                .username(userDetails.getUsername()).build();

        return MessageResponse.builder().message("Berhasil login").data(authResponse).statusCode(200).build();
    }

    public MessageResponse register(RegisterRequest register) {
        User user = new User();
        user.setName(register.getName());
        user.setUsername(register.getUsername());
        user.setEmail(register.getEmail());
        user.setPassword(passwordEncoder.encode(register.getPassword()));

        this.userRepository.save(user);

        return MessageResponse.builder().message("Berhasil register").data(user).statusCode(200).build();

    }
}
