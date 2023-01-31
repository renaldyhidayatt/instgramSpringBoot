package com.sanedge.instagramclone.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sanedge.instagramclone.models.Follow;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Long> {
    @Query("SELECT f FROM Follow f WHERE f.fromFollows.id = :userId")
    List<Follow> findAllByFromFollows(@Param("userId") Long userId);

    @Query("SELECT f FROM Follow f WHERE f.getUserFollows.id = :userId")
    List<Follow> findAllByGetUserFollows(@Param("userId") Long userId);

    @Query("SELECT f FROM Follow f WHERE f.fromFollows.id = :fromfollowid and f.getUserFollows.id = :getuserfollowid")
    Optional<Follow> findByUserFromAndUserTo(@Param("fromfollowid") Long fromfollowId,
            @Param("getuserfollowid") Long getuserFollowId);
}
