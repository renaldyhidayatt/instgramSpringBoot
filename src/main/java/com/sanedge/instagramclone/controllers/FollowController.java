package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.service.impl.FollowImplService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/follows")
public class FollowController {
    
    @Autowired
    private  FollowImplService followImplService;


    @PostMapping("/{userId}")
    public ResponseEntity<MessageResponse> followUser(@PathVariable Long userId) {
        MessageResponse response = followImplService.findByUserFromUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<MessageResponse> unFollowUser(@PathVariable Long userId) {
        MessageResponse response = followImplService.findByUserFromUser(userId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
