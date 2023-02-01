package com.sanedge.instagramclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanedge.instagramclone.models.Photo;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Long> {
    @Query("SELECT p FROM Photo p WHERE p.uploadedBy.id = :userId")
    List<Photo> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Photo p LEFT JOIN FETCH p.uploadedBy u LEFT JOIN FETCH p.getComments c LEFT JOIN FETCH c.postedBy LEFT JOIN FETCH p.getLikes l WHERE p.id = :id")
    Optional<Photo> findByIdWithLikesAndComments(@Param("id") Long id);

    @Query("SELECT p FROM Photo p LEFT JOIN FETCH p.uploadedBy u LEFT JOIN FETCH p.getLikes l LEFT JOIN FETCH p.getComments c WHERE p.id = :id ORDER BY c.createdAt DESC")
    Optional<Photo> findByIdWithUploaderAndLikesAndRecentComments(@Param("id") Long id);

}
