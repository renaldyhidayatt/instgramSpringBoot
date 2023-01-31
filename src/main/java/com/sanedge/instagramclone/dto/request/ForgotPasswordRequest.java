package com.sanedge.instagramclone.dto.request;

import lombok.Data;

@Data
public class ForgotPasswordRequest {
    private String passwordOld;
    private String password;
    private String passwordConfirm;
}
