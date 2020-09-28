package com.blog.blogger.repository;

import com.blog.blogger.modul.Blogger;
import com.blog.blogger.modul.Post;
import com.blog.blogger.modul.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByBlogger(Blogger blogger);
    List<Post> findByUser(User user);
}
