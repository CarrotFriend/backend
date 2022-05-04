package com.carrotfriend.dto.user;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateDto {
    private Long id;
    private String pw;
    private String nickName;
    private LocalDate birthday;
    private String email;
}
