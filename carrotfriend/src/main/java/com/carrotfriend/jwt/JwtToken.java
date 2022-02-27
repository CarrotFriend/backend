package com.carrotfriend.jwt;

import lombok.Builder;

import java.util.Date;

@Builder
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpiration;
}
