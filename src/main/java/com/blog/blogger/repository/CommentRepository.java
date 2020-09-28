package com.blog.blogger.repository;

import com.blog.blogger.modul.Comment;
import com.blog.blogger.modul.Post;
import com.blog.blogger.modul.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByPost(Post post);

    List<Comment> findAllByUser(User user);
}
