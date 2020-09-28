package com.blog.blogger.repository;

import com.blog.blogger.modul.Post;
import com.blog.blogger.modul.User;
import com.blog.blogger.modul.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long> {

    Optional<Vote> findTopByPostAndUserOrderByVoteIdDesc(Post post, User currentuser);
}
