package com.carrotfriend.dto.post;

import com.carrotfriend.dto.user.CategoryDto;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateDto {
    private Long userId;
    private String title;
    private List<ImageDto> imageList;
    private String content;
    private CategoryDto category;
    private List<TagDto> tagList;
}
