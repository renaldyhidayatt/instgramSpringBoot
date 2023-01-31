package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.service.impl.LikeImplService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/like")
public class LikeController {

    @Autowired
    private LikeImplService likeImplService;


    @PostMapping("/{photo}")
    public ResponseEntity<MessageResponse> create(@RequestParam("photo") Long id){
        MessageResponse messageResponse = this.likeImplService.create(id);

        return ResponseEntity.ok(messageResponse);
    }
}
