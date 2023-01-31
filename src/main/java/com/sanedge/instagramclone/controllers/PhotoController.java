package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.sanedge.instagramclone.dto.request.PhotoRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.service.impl.PhotoImplService;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/photo")
public class PhotoController {

    @Autowired
    PhotoImplService photoImplService;

    @GetMapping("/{id}")
    public ResponseEntity<MessageResponse> findById(@RequestParam("id") Long id) {
        MessageResponse messageResponse = this.photoImplService.findById(id);

        return ResponseEntity.ok(messageResponse);
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponse> uploadPhoto(@RequestParam("file") MultipartFile file, @Valid @RequestBody PhotoRequest photoRequest){
        MessageResponse messageResponse = this.photoImplService.uploadPhoto(photoRequest, file);

        return ResponseEntity.ok(messageResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<MessageResponse> deleteById(@RequestParam("id") Long id){
        MessageResponse messageResponse = this.photoImplService.deleteById(id);

        return ResponseEntity.ok(messageResponse);
    }
}
