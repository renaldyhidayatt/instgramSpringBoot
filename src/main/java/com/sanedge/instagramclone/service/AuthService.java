package com.sanedge.instagramclone.service;

import com.sanedge.instagramclone.dto.request.AuthRequest;
import com.sanedge.instagramclone.dto.request.RegisterRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;

public interface AuthService {
    public MessageResponse loginUser(AuthRequest authRequest);

    public MessageResponse register(RegisterRequest registerRequest);
}
