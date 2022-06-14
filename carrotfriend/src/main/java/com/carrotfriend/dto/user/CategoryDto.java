package com.carrotfriend.dto.user;

import com.carrotfriend.domain.Category;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class CategoryDto {
    private Long id;
    private String name;

    public static CategoryDto of(Category category){
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }
}
