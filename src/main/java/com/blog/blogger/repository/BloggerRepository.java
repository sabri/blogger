package com.blog.blogger.repository;

import com.blog.blogger.modul.Blogger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BloggerRepository extends JpaRepository<Blogger, Long> {

    Optional<Blogger> findByName(String name);
}
