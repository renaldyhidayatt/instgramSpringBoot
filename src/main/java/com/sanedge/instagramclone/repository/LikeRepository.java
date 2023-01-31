package com.sanedge.instagramclone.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanedge.instagramclone.models.Like;

@Repository
public interface LikeRepository extends JpaRepository<Like, Long> {
    @Query("SELECT l FROM Like l WHERE l.photo.id = :photoId AND l.user.id = :userId")
    Optional<Like> findByPhotoIdAndUserId(@Param("photoId") Long photoId, @Param("userId") Long userId);
}
