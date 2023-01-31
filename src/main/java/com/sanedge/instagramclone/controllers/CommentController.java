package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.instagramclone.dto.request.CommentRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.service.impl.CommentImplService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentImplService commentImplService;

    @PostMapping("/{photo}")
    public ResponseEntity<MessageResponse> create(@RequestParam("photo") Long photoId,
            @Valid @RequestBody CommentRequest commentRequest) {
        MessageResponse messageResponse = this.commentImplService.create(commentRequest, photoId);

        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/{id}")
    public ResponseEntity<MessageResponse> update(@RequestParam("id") Long id,
            @Valid @RequestBody CommentRequest commentRequest) {
        MessageResponse messageResponse = this.commentImplService.updateComment(id, commentRequest);

        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteComment(@RequestParam("id") Long id) {
        MessageResponse messageResponse = this.commentImplService.deleteComment(id);

        return ResponseEntity.ok(messageResponse);
    }
}
