package com.carrotfriend.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class JoinDto {
    private String userId;
    private String pw;
    private String userName;
    private String nickName;
    private LocalDate birthday;
    private String email;
}
