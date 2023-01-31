package com.sanedge.instagramclone.service;

import com.sanedge.instagramclone.dto.response.MessageResponse;

public interface SearchService {
    public MessageResponse searchUser(String term);
}
