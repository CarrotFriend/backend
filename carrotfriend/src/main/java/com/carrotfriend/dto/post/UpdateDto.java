package com.carrotfriend.dto.post;

import com.carrotfriend.dto.user.CategoryDto;
import lombok.Getter;

import java.util.List;

@Getter
public class UpdateDto {
    private Long id;
    private String title;
    private String content;
    private List<ImageDto> imageList;
    private List<TagDto> tagList;
    private CategoryDto category;
}
