package com.blog.blogger.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Bloggerdto {
    private Long id;
    private String name;
    private String description;
    private Integer numberOfPosts;
}
