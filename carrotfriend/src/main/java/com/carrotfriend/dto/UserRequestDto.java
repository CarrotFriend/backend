package com.carrotfriend.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

public class UserRequestDto {
    @Builder @Getter
    public static class JoinUser {
        private String userId;
        private String pw;
        private String nickName;
        private LocalDate birthday;
        private String email;
    }
}
