package com.carrotfriend.dto.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDto {
    private String text;

    public static TagDto of(String text){
        if(text == null) return null;
        return TagDto.builder().text(text).build();
    }
}
