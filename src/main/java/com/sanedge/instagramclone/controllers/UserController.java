package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanedge.instagramclone.dto.request.ForgotPasswordRequest;
import com.sanedge.instagramclone.dto.request.UserUpdateRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.service.impl.UserImplService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserImplService userImplService;

    @GetMapping("/me")
    public ResponseEntity<?> me() {
        User user = this.userImplService.getCurrentUser();

        return ResponseEntity.ok(user);
    }

    @GetMapping("/profile")
    public ResponseEntity<MessageResponse> getUserProfile(@PathVariable String username, @RequestParam Integer page,
            @RequestParam Integer pageSize) {
        MessageResponse messageResponse = this.userImplService.getUserProfile(username, page, pageSize);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/updateuser")
    public ResponseEntity<MessageResponse> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
        MessageResponse messageResponse = this.userImplService.updateUser(userUpdateRequest);

        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/updateavatar")
    public ResponseEntity<MessageResponse> updateUserAvatar(@RequestParam("file") MultipartFile file) {
        MessageResponse messageResponse = this.userImplService.updateUserAvatar(file);

        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/updatepassword")
    public ResponseEntity<MessageResponse> updateUserPassword(
            @Valid @RequestBody ForgotPasswordRequest passwordRequest) {
        MessageResponse messageResponse = this.userImplService.updateUserPassword(passwordRequest);

        return ResponseEntity.ok(messageResponse);
    }
}
