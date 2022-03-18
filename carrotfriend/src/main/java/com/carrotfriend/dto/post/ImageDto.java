package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Image;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ImageDto {
    private Long id;
    private String locate;
    private String imageName;
    private Long fileSize;
    public static ImageDto of(Image image){
        return ImageDto.builder()
                .id(image.getId())
                .locate(image.getLocate())
                .fileSize(image.getFileSize())
                .imageName(image.getImageName())
                .build();
    }
}
