package com.sanedge.instagramclone.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sanedge.instagramclone.dto.response.MessageResponse;
import com.sanedge.instagramclone.models.User;
import com.sanedge.instagramclone.repository.UserRepository;
import com.sanedge.instagramclone.service.SearchService;

@Service
public class SearchImplService implements SearchService {
    private UserRepository userRepository;

    @Autowired
    public SearchImplService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public MessageResponse searchUser(String term) {
        if (term.isEmpty()) {
            return MessageResponse.builder().message("yang dicari kosong").build();
        }

        List<User> search = this.userRepository.findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCase(term);

        return MessageResponse.builder().message("Berhasil mendapatkan data").data(search).statusCode(200).build();

    }
}
