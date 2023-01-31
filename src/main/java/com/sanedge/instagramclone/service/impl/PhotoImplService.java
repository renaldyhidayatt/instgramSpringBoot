package com.sanedge.instagramclone.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.sanedge.instagramclone.dto.request.PhotoRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.dto.response.PhotoResponse;
import com.sanedge.instagramclone.exception.NotFoundException;
import com.sanedge.instagramclone.models.Like;
import com.sanedge.instagramclone.models.Photo;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.LikeRepository;
import com.sanedge.instagramclone.repository.PhotoRepository;

@Service
public class PhotoImplService {

    private PhotoRepository photoRepository;
    private FileStorageServiceImpl fileStorageServiceImpl;
    private UserImplService userImplService;
    private LikeRepository likeRepository;

    @Autowired
    public PhotoImplService(PhotoRepository photoRepository, FileStorageServiceImpl fileStorageServiceImpl,
            UserImplService userImplService, LikeRepository likeRepository) {
        this.photoRepository = photoRepository;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
        this.userImplService = userImplService;
        this.likeRepository = likeRepository;
    }

    public MessageResponse findById(Long id) {
        Photo photo = this.photoRepository.findByIdWithLikesAndCommentsCount(id)
                .orElseThrow(() -> new NotFoundException("photo tersebut  tidak ada"));

        boolean isAuthor = false;
        if (this.userImplService.getCurrentUser().getId() == photo.getUploadedBy().getId()) {
            isAuthor = true;
        }

        boolean isLiked = false;
        Like like = this.likeRepository.findByPhotoIdAndUserId(photo.getId(),
                this.userImplService.getCurrentUser().getId())
                .orElseThrow(() -> new NotFoundException("like tersebut tidak ada"));

        isLiked = true;

        PhotoResponse response = new PhotoResponse();

        response.setPhoto(photo);
        response.setAuthor(isAuthor);
        response.setLiked(isLiked);

        return MessageResponse.builder().message("Berhasil mendapatkan data").data(response).statusCode(200).build();
    }

    public MessageResponse uploadPhoto(PhotoRequest photoRequest, MultipartFile file) {
        User getCurrentUser = this.userImplService.getCurrentUser();
        String filename = this.fileStorageServiceImpl.storeFile(file);
        Photo photo = new Photo();

        photo.setUploadedBy(getCurrentUser);
        photo.setKey(photoRequest.getKey());
        photo.setBody(photoRequest.getBody());
        photo.setPhoto_url(filename);

        this.photoRepository.save(photo);

        Photo photoById = this.photoRepository.findByIdWithUploaderAndLikesAndRecentComments(photo.getId())
                .orElseThrow(() -> new NotFoundException("photo tidak ditemukan "));

        boolean isAuthor = false;
        boolean isLiked = false;

        if (photoById.getUploadedBy().getId().equals(getCurrentUser.getId())) {
            isAuthor = true;
        }

        List<Like> likes = photoById.getGetLikes();
        for (Like like : likes) {
            if (like.getUser().getId().equals(getCurrentUser.getId())) {
                isLiked = true;
                break;
            }
        }

        PhotoResponse response = new PhotoResponse();

        response.setPhoto(photoById);
        response.setAuthor(isAuthor);
        response.setLiked(isLiked);

        return MessageResponse.builder().message("Berhasil membuat data").data(response).statusCode(200).build();
    }

    public MessageResponse deleteById(Long id) {
        Photo photo = this.photoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("photo tidak ditemukan"));

        User user = this.userImplService.getCurrentUser();

        if (photo.getUploadedBy().getId() != user.getId()) {
            return MessageResponse.builder().message("tidak sama").build();
        }

        this.fileStorageServiceImpl.deleteByName(photo.getPhoto_url());

        this.photoRepository.delete(photo);

        return MessageResponse.builder().message("Berhasil menghapus  data").statusCode(200).build();
    }

}
