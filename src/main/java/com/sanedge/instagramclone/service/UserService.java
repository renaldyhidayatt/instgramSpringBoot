package com.sanedge.instagramclone.service;

import org.springframework.web.multipart.MultipartFile;

import com.sanedge.instagramclone.dto.request.ForgotPasswordRequest;
import com.sanedge.instagramclone.dto.request.UserUpdateRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.models.User;

public interface UserService {
    public User getCurrentUser();

    public MessageResponse getUserProfile(String username, Integer page, Integer pageSize);

    public MessageResponse updateUser(UserUpdateRequest userUpdateRequest);

    public MessageResponse updateUserAvatar(MultipartFile file);

    public MessageResponse updateUserPassword(ForgotPasswordRequest passwordRequest);
}
