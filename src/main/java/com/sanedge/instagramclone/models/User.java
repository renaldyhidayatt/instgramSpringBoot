package com.sanedge.instagramclone.models;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String username;
    
    private String password;
    private String bio;
    private String phone;
    private String key;
    private String avatar_url;

    @OneToMany(mappedBy = "uploadedBy")
    private List<Photo> photoUploads;

    @ManyToMany
    @JoinTable(name = "likes", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "like_id"))
    private List<Like> userLike;

    @OneToMany(mappedBy = "postedBy")
    private List<Comment> getComments;

    @OneToMany(mappedBy = "fromFollows")
    private List<Follow> getFollows;

    @OneToMany(mappedBy = "getUserFollows")
    private List<Follow> getFollowers;
}
