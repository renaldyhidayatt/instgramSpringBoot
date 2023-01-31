package com.sanedge.instagramclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanedge.instagramclone.models.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("SELECT p FROM Photo p WHERE p.uploadedBy.id = :userId")
    List<Photo> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Photo p LEFT JOIN p.getLikes l ON p.id = l.photo.id LEFT JOIN p.getComments c ON p.id = c.photo.id LEFT JOIN p.uploadedBy u ON p.user_id = u.id WHERE p.id = :id GROUP BY p, l, c, u.id")
    Optional<Photo> findByIdWithLikesAndCommentsCount(@Param("id") Long id);

    @Query("SELECT p FROM Photo p LEFT JOIN FETCH p.uploadedBy u LEFT JOIN FETCH p.getLikes l LEFT JOIN FETCH p.getComments c WHERE p.id = :id ORDER BY c.createdAt DESC")
    Optional<Photo> findByIdWithUploaderAndLikesAndRecentComments(@Param("id") Long id);

}
