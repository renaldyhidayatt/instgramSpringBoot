package com.sanedge.instagramclone.repository;

import java.util.Optional;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanedge.instagramclone.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("SELECT u FROM User u WHERE LOWER(u.username) LIKE LOWER(CONCAT('%', :term, '%')) OR LOWER(u.name) LIKE LOWER(CONCAT('%', :term, '%'))")
    List<User> findByUsernameContainingIgnoreCaseOrNameContainingIgnoreCase(@Param("term") String term);

    @Query("SELECT u FROM User u JOIN FETCH u.getFollows f WHERE f.fromFollows.id = :userId")
    List<User> findFollowsByUserId(@Param("userId") Long userId);

}
