package com.carrotfriend.dto.user;

import com.carrotfriend.domain.Category;
import com.carrotfriend.domain.User;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class UserDto{
    @JsonProperty
    private Long id;
    @JsonProperty
    private String userId;
    @JsonProperty
    private String nickName;
    @JsonProperty
    private List<CategoryDto> categoryList;

    public static UserDto of(User user){
        return UserDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .categoryList(user.getUserToCategoryList()
                                    .stream()
                                    .map(c->CategoryDto.of(c.getCategory()))
                                    .collect(Collectors.toList()))
                .build();
    }
}
