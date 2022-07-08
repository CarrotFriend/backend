package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Post;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.dto.user.UserDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter@Setter
@Builder
public class PostDto {
    private Long postId;
    private String title;
    private String regDate;
    private List<ImageDto> imageList;
    private String content;
    private CategoryDto category;
    private List<TagDto> tagList;
    private UserDto user;
    private int view;
    public static PostDto of(Post post){
        PostDto postDto = PostDto.builder()
                .postId(post.getId())
                .user(UserDto.of(post.getUser()))
                .title(post.getTitle())
                .regDate(post.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .content(post.getContent())
                .imageList(post.getImageList().stream().map(i->ImageDto.of(i)).collect(Collectors.toList()))
                .tagList(post.getTagList().stream().map(t->TagDto.of(t)).collect(Collectors.toList()))
                .category(CategoryDto.of(post.getCategory()))
                .build();
        return postDto;

    }
}
