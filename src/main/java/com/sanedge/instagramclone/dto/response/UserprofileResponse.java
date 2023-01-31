package com.sanedge.instagramclone.dto.response;

import com.sanedge.instagramclone.models.User;

import lombok.Data;

@Data
public class UserprofileResponse {
    private User user;
    private int countPhotos;
    private int countFollows;
    private int countFollowers;
    private boolean isProfile;
    private boolean isFollow;
}
