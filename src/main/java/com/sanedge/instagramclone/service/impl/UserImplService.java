package com.sanedge.instagramclone.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

import com.sanedge.instagramclone.dto.request.ForgotPasswordRequest;
import com.sanedge.instagramclone.dto.request.UserUpdateRequest;
import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.dto.response.UserprofileResponse;
import com.sanedge.instagramclone.exception.NotFoundException;
import com.sanedge.instagramclone.models.Follow;
import com.sanedge.instagramclone.models.Photo;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.FollowRepository;
import com.sanedge.instagramclone.repository.PhotoRepository;
import com.sanedge.instagramclone.repository.UserRepository;

@Service
public class UserImplService {
    private UserRepository userRepository;
    private PhotoRepository photoRepository;
    private FollowRepository followRepository;
    private FileStorageServiceImpl fileStorageServiceImpl;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserImplService(UserRepository userRepository, PhotoRepository photoRepository,
            FollowRepository followRepository, FileStorageServiceImpl fileStorageServiceImpl,
            PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        this.followRepository = followRepository;
        this.fileStorageServiceImpl = fileStorageServiceImpl;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        return userRepository.findByUsername(authentication.getName())
                .orElseThrow(
                        () -> new UsernameNotFoundException("User name not found - " + authentication.getName()));

    }

    public MessageResponse getUserProfile(String username, Integer page, Integer pageSize) {
        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found"));

        List<Photo> countPhotos = photoRepository.findAllByUserId(user.getId());
        List<Follow> countFollows = followRepository.findAllByFromFollows(user.getId());
        List<Follow> countFollowers = followRepository.findAllByGetUserFollows(user.getId());

        boolean isProfile = user.getId().equals(this.getCurrentUser().getId());

        Optional<Follow> isFollow = followRepository.findByUserFromAndUserTo(this.getCurrentUser().getId(),
                user.getId());

        UserprofileResponse response = new UserprofileResponse();
        response.setUser(user);
        response.setCountPhotos(countPhotos.size());
        response.setCountFollows(countFollows.size());
        response.setCountFollowers(countFollowers.size());
        response.setProfile(isProfile);
        response.setFollow(isFollow.isPresent());

        return MessageResponse.builder().message("Berhasil mendapatkan data").data(response).statusCode(200).build();
    }

    public MessageResponse updateUser(UserUpdateRequest userUpdateRequest) {
        User user = this.getCurrentUser();

        user.setName(userUpdateRequest.getName());
        user.setEmail(userUpdateRequest.getEmail());
        user.setUsername(userUpdateRequest.getUsername());
        user.setPhone(userUpdateRequest.getPhone());
        user.setBio(userUpdateRequest.getBio());

        this.userRepository.save(user);

        return MessageResponse.builder().message("berhasil mengupdate data").data(userUpdateRequest).statusCode(200)
                .build();
    }

    public MessageResponse updateUserAvatar(MultipartFile file) {
        User getCurrentUser = this.getCurrentUser();
        String filename = this.fileStorageServiceImpl.storeFile(file);

        getCurrentUser.setAvatar_url(filename);

        this.userRepository.save(getCurrentUser);

        return MessageResponse.builder().message("Berhasil mengupdate data").data(getCurrentUser).statusCode(200)
                .build();
    }

    public MessageResponse updateUserPassword(ForgotPasswordRequest request) {
        User user = this.getCurrentUser();

        if (!this.passwordEncoder.matches(request.getPasswordOld(), user.getPassword())) {
            return MessageResponse.builder().message("Password lama tidak sama").build();
        }

        if (request.getPassword() != request.getPasswordConfirm()) {
            return MessageResponse.builder().message("Password baru tidak sama").build();
        }

        String passwordBaru = this.passwordEncoder.encode(request.getPassword());

        user.setPassword(passwordBaru);

        return MessageResponse.builder().message("Berhasil mengupdate data").data(user).statusCode(200).build();
    }
}
