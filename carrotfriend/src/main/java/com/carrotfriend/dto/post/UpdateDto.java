package com.carrotfriend.dto.post;

import lombok.Getter;

import java.util.List;

@Getter
public class UpdateDto {
    private Long id;
    private String title;
    private List<ImageDto> imageList;
    private String content;
}
