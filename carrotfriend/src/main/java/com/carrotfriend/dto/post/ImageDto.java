package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDto {
    private Long id;
    private String src;
    public static ImageDto of(Image image){
        return ImageDto.builder()
                .id(image.getId())
                .src(image.getSrc())
                .build();
    }
}
