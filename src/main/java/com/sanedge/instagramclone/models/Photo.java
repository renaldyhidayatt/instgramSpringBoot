package com.sanedge.instagramclone.models;

import java.util.List;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
public class Photo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;
    private String key;
    private String photo_url;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User uploadedBy;

    @OneToMany(mappedBy = "photo")
    private List<Like> getLikes;

    @OneToMany(mappedBy = "photo")
    private List<Comment> getComments;
}
