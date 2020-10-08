package com.blog.blogger.service;

import com.blog.blogger.controller.dto.Bloggerdto;
import com.blog.blogger.exceptions.SpringException;
import com.blog.blogger.mapper.BloggerMap;
import com.blog.blogger.modul.Blogger;
import com.blog.blogger.repository.BloggerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
@Slf4j
public class BloggerService {

    private final BloggerRepository bloggerRepository;
    private final BloggerMap bloggerMap;

    @Transactional
    public Bloggerdto save(Bloggerdto bloggerdto) {
        Blogger save = bloggerRepository.save(bloggerMap.maptoToBlogger(bloggerdto));
        bloggerdto.setId(save.getId());
        return bloggerdto;
    }

    @Transactional(readOnly = true)
    public List<Bloggerdto> getAll() {
        return bloggerRepository.findAll()
                .stream()
                .map(bloggerMap::mapBloggerToDto)
                .collect(toList());
    }

    public Bloggerdto getBlogger(Long id) {
        Blogger subreddit = bloggerRepository.findById(id)
                .orElseThrow(() -> new SpringException("No Blogger found with ID - " + id));
        return bloggerMap.mapBloggerToDto(subreddit);
    }
}
