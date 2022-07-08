package com.carrotfriend.dto.user;

import lombok.Getter;

import java.util.List;

@Getter
public class UserCateDto {
    private Long id;
    private List<CategoryDto> categoryList;
}
