package com.sanedge.instagramclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.exception.NotFoundException;
import com.sanedge.instagramclone.models.Like;
import com.sanedge.instagramclone.models.Photo;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.LikeRepository;
import com.sanedge.instagramclone.repository.PhotoRepository;

@Service
public class LikeImplService {
    private LikeRepository likeRepository;
    private PhotoRepository photoRepository;
    private UserImplService userImplService;

    @Autowired
    public LikeImplService(LikeRepository likeRepository, UserImplService userImplService,
            PhotoRepository photoRepository) {
        this.likeRepository = likeRepository;
        this.userImplService = userImplService;
        this.photoRepository = photoRepository;
    }

    public MessageResponse create(Long photoId) {
        User user = this.userImplService.getCurrentUser();
        Photo photo = this.photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("photo tidak ditemukan"));

        Like like = this.likeRepository.findByPhotoIdAndUserId(photoId, user.getId())
                .orElseThrow(() -> new NotFoundException("like tidak ditemukan"));

        like.setPhoto(photo);
        like.setUser(user);
        this.likeRepository.save(like);

        return MessageResponse.builder().message("Berhasil membuat data").data(like).statusCode(200).build();
    }

}
