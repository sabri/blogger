package com.blog.blogger.controller;
import com.blog.blogger.service.BloggerService;
import com.blog.blogger.controller.dto.Bloggerdto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogger")
@AllArgsConstructor
@Slf4j
public class BloggerController {

    private final BloggerService bloggerService;

    @PostMapping
    public ResponseEntity<Bloggerdto> createBlogger(@RequestBody Bloggerdto bloggerdto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(bloggerService.save(bloggerdto));
    }

    @GetMapping
    public ResponseEntity<List<Bloggerdto>> getAllSubreddits() {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bloggerService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bloggerdto> getSubreddit(@PathVariable Long id) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(bloggerService.getBlogger(id));
    }}
