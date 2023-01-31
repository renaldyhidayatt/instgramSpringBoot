package com.sanedge.instagramclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.instagramclone.dto.request.CommentRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.exception.NotFoundException;
import com.sanedge.instagramclone.models.Comment;
import com.sanedge.instagramclone.models.Photo;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.CommentRepository;
import com.sanedge.instagramclone.repository.PhotoRepository;
import com.sanedge.instagramclone.service.CommentService;

@Service
public class CommentImplService implements CommentService {
    private CommentRepository commentRepository;
    private PhotoRepository photoRepository;
    private UserImplService userImplService;

    @Autowired
    public CommentImplService(CommentRepository commentRepository, PhotoRepository photoRepository,
            UserImplService userImplService) {
        this.commentRepository = commentRepository;
        this.photoRepository = photoRepository;
        this.userImplService = userImplService;
    }

    @Override
    public MessageResponse create(CommentRequest commentRequest, Long photoId) {
        User user = this.userImplService.getCurrentUser();

        Photo photo = this.photoRepository.findById(photoId)
                .orElseThrow(() -> new NotFoundException("not found photo id"));

        Comment comment = new Comment();
        comment.setBody(commentRequest.getBody());
        comment.setPostedBy(user);
        comment.setPhoto(photo);

        this.commentRepository.save(comment);

        return MessageResponse.builder().message("berhasil membuat comment").data(comment).statusCode(200).build();
    }

    @Override
    public MessageResponse updateComment(Long id, CommentRequest commentRequest) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found comment"));

        comment.setBody(commentRequest.getBody());
        this.commentRepository.save(comment);

        return MessageResponse.builder().message("berhasil update comment").data(comment).statusCode(200).build();
    }

    @Override
    public MessageResponse deleteComment(Long id) {
        Comment comment = this.commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("not found comment"));

        this.commentRepository.delete(comment);

        return MessageResponse.builder().message("Berhasil delete comment").statusCode(200).build();
    }
}
