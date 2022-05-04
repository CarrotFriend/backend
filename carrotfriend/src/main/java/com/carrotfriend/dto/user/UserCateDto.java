package com.carrotfriend.dto.user;

import lombok.Getter;

import java.util.List;

@Getter
public class UserCateDto {
    private UserDto userDto;
    private List<CategoryDto> categoryDto;
}
