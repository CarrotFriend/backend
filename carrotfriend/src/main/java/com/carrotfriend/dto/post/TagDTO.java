package com.carrotfriend.dto.post;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TagDTO {
    private Long id;
    private String text;
}
