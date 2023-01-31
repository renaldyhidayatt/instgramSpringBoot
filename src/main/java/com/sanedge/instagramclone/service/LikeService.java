package com.sanedge.instagramclone.service;

import com.sanedge.instagramclone.dto.response.MessageResponse;

public interface LikeService {
    public MessageResponse create(Long photoId);
}
