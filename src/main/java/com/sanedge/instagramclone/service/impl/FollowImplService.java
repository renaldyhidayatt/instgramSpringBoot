package com.sanedge.instagramclone.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.exception.NotFoundException;
import com.sanedge.instagramclone.models.Follow;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.FollowRepository;
import com.sanedge.instagramclone.repository.UserRepository;

@Service
public class FollowImplService {
    private FollowRepository followRepository;
    private UserRepository userRepository;
    private UserImplService userImplService;

    @Autowired
    public FollowImplService(FollowRepository followRepository, UserRepository userRepository,
            UserImplService userImplService) {
        this.followRepository = followRepository;
        this.userRepository = userRepository;
        this.userImplService = userImplService;
    }


    public MessageResponse findByUserFromUser(Long userId){
        User getCurrent = this.userImplService.getCurrentUser();
        User findParamUserId = this.userRepository.findById(userId).orElseThrow(() -> new NotFoundException("Tidak id tersebut"));

        if(getCurrent.equals(findParamUserId)){
            throw new NotFoundException("Kamu tidak bisa mengikuti dirimu sendiri");
        }
        Optional<Follow> follow = this.followRepository.findByUserFromAndUserTo(getCurrent.getId(), findParamUserId.getId());
        if(follow.isPresent()){
            this.followRepository.delete(follow.get());
        }else{
            Follow newFollow = new Follow();
            newFollow.setFromFollows(getCurrent);
            newFollow.setToFollows(findParamUserId);
            this.followRepository.save(newFollow);
        }

        return MessageResponse.builder().message("Berhasil melakukan follow/unfollow").statusCode(200).build();


    } 

    
    
}
