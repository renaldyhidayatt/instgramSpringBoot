package com.sanedge.instagramclone.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.service.impl.SearchImplService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/search")
public class SearchController {

    @Autowired
    SearchImplService searchImplService;

    @GetMapping("/{term}")
    public ResponseEntity<MessageResponse> searchTerm(@RequestParam("term") String term) {
        MessageResponse messageResponse = this.searchImplService.searchUser(term);

        return ResponseEntity.ok(messageResponse);
    }
}
