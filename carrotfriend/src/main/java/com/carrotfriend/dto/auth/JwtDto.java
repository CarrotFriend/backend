package com.carrotfriend.dto.auth;

import com.carrotfriend.jwt.JwtToken;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Date;

@Builder
public class JwtDto {
    @JsonProperty
    private String grantType;
    @JsonProperty
    private String accessToken;
    @JsonProperty
    private String refreshToken;
    @JsonProperty
    private Date refreshTokenExpireTime;

    public static JwtDto of(JwtToken jwtToken){
        return JwtDto.builder()
                .accessToken(jwtToken.getAccessToken())
                .refreshToken(jwtToken.getRefreshToken())
                .grantType(jwtToken.getGrantType())
                .refreshTokenExpireTime(jwtToken.getRefreshTokenExpiration())
                .build();
    }
}
