package com.carrotfriend.dto.auth;

import lombok.Getter;

@Getter
public class NewTokenDto {
    private String accessToken;
    private String refreshToken;
}
