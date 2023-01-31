package com.sanedge.instagramclone.dto.response;

import com.sanedge.instagramclone.models.Photo;

import lombok.Data;

@Data
public class PhotoResponse {
    private Photo photo;
    private boolean Author;
    private boolean Liked;
}
