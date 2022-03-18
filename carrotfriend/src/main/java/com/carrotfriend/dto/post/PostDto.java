package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Image;
import com.carrotfriend.domain.Post;
import lombok.Builder;
import lombok.Getter;

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

    public static PostDto of(Post post){
        return PostDto.builder()
                .id(post.getId())
                .userId(post.getUser().getUserId())
                .title(post.getTitle())
                .imageList(post.getImageList().stream().map(i->ImageDto.of(i)).collect(Collectors.toList()))
                .content(post.getContent())
                .build();
    }
}
