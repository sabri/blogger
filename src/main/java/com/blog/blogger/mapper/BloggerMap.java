package com.blog.blogger.mapper;


import com.blog.blogger.controller.dto.Bloggerdto;
import com.blog.blogger.modul.Blogger;
import com.blog.blogger.modul.Post;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


import java.util.List;

@Mapper(componentModel = "spring")
public interface BloggerMap {

    @Mapping(target = "numberOfPosts", expression = "java(mapPosts(subreddit.getPosts()))")
   Bloggerdto mapBloggerToDto(Blogger blogger);

    default Integer mapPosts(List<Post> numberOfPosts) {
        return numberOfPosts.size();
    }

    @InheritInverseConfiguration
    @Mapping(target = "posts", ignore = true)
    Blogger maptoToBlogger(Bloggerdto bloggerdto);
}
