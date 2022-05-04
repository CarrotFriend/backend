package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Post;
import com.carrotfriend.dto.user.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Builder
public class PostDto {
    private Long id;
    private String userId;
    private String title;
    private List<ImageDto> imageList;
    private String content;
    private CategoryDto category;

    public static PostDto of(Post post){
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getUserId())
                .title(post.getTitle())
                .content(post.getContent())
                .imageList(Collections.emptyList())
                .category(CategoryDto.of(post.getCategory()))
                .build();
        post.getImageList().forEach(i->postDto.getImageList().add(ImageDto.of(i)));
        return postDto;
    }
}
