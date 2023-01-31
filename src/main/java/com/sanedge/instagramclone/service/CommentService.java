package com.sanedge.instagramclone.service;

import com.sanedge.instagramclone.dto.request.CommentRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;

public interface CommentService {
    public MessageResponse create(CommentRequest commentRequest, Long photoId);

    public MessageResponse updateComment(Long id, CommentRequest commentRequest);

    public MessageResponse deleteComment(Long id);
}
