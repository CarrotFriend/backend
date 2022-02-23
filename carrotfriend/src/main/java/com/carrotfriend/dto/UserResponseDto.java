package com.carrotfriend.dto;

import com.carrotfriend.domain.Gender;
import lombok.Builder;
import lombok.ToString;
import java.time.LocalDateTime;

public class UserResponseDto {
    @Builder
    @ToString
    public static class User{
        private Long id;
        private String userID;
        private String nickName;
        private LocalDateTime birthday;
        private String email;
        private Gender gender;
    }
}
