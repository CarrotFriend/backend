package com.carrotfriend.dto.post;

import com.carrotfriend.domain.Image;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {
    private Long imageId;
    private String src;
    public static ImageDto of(Image image){
        if(image == null) return null;
        return ImageDto.builder()
                .imageId(image.getId())
                .src(image.getSrc())
                .build();
    }
}
