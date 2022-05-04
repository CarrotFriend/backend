package com.carrotfriend.dto.user;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class JoinDto {
    private String userId;
    private String pw;
    private String nickName;
    private LocalDate birthday;
    private String email;
}
