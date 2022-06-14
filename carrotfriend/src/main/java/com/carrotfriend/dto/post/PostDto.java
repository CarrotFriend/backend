package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Post;
import com.carrotfriend.dto.user.CategoryDto;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
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
    private LocalDate regDate;
    private List<ImageDto> imageList;
    private String content;
    private CategoryDto category;
    private List<TagDTO> tags;
    public static PostDto of(Post post){
        PostDto postDto = PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getUserId())
                .title(post.getTitle())
                .regDate(post.getRegDate())
                .content(post.getContent())
                .imageList(Collections.emptyList())
                .category(CategoryDto.of(post.getCategory()))
                .build();
        return postDto;
    }
}
