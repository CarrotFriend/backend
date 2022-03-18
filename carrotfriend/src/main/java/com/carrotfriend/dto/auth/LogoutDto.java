package com.carrotfriend.dto.auth;

import lombok.Getter;

@Getter
public class LogoutDto {
    private String refreshToken;
    private String accessToken;
}
