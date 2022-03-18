package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Image;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateDto {
    private String userId;
    private String title;
    private List<ImageDto> imageList;
    private String content;
}
