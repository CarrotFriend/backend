package com.carrotfriend.dto.user;

import com.carrotfriend.domain.User;
import com.carrotfriend.dto.post.ImageDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto{
    private Long id;
    private String userId;
    private String userName;
    private String nickName;
    private String email;
    private String regDate;
    private Double temperature;
    private String birthday;
    private ImageDto image;
    private List<CategoryDto> categoryList;

    public static UserDto of(User user){
        return UserDto.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .userName(user.getName())
                .nickName(user.getNickName())
                .email(user.getEmail())
                .regDate(user.getRegDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .birthday(user.getBirthday().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))
                .image(ImageDto.of(user.getImage()))
                .temperature(user.getTemperature())
                .categoryList(user.getUserToCategoryList()
                        .stream()
                        .map(c->CategoryDto.of(c.getCategory()))
                        .collect(Collectors.toList()))
                .build();
    }
}
