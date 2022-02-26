package com.carrotfriend.dto;

import com.carrotfriend.domain.Gender;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

public class UserRequestDto {
    @Builder @Getter
    public static class User{
        private String userID;
        private String pw;
        private String nickName;
        private LocalDateTime birthday;
        private String email;
        private Gender gender;
    }
}