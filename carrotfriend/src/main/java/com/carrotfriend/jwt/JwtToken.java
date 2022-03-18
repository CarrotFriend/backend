package com.carrotfriend.jwt;

import lombok.Builder;
import lombok.Getter;

import java.util.Date;

@Getter
@Builder
public class JwtToken {
    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Date refreshTokenExpiration;
}
