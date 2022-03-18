package com.carrotfriend.dto.user;

import com.carrotfriend.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CategoryDto {
    private Long id;
    private String code;
    private String name;

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .code(category.getCode())
                .name(category.getName())
                .build();
    }
}
