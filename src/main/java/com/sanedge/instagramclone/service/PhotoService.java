package com.sanedge.instagramclone.service;

import org.springframework.web.multipart.MultipartFile;

import com.sanedge.instagramclone.dto.request.PhotoRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;

public interface PhotoService {
    public MessageResponse findById(Long id);

    public MessageResponse uploadPhoto(PhotoRequest photoRequest, MultipartFile file);

    public MessageResponse deleteById(Long id);
}
