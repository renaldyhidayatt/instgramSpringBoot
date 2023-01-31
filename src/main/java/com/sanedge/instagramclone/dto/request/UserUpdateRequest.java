package com.sanedge.instagramclone.dto.request;

import lombok.Data;

@Data
public class UserUpdateRequest {
    private String name;
    private String email;
    private String username;
    private String bio;
    private String phone;
}
