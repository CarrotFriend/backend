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

    public static JwtDto of(JwtToken jwtToken){
        return JwtDto.builder()
                .accessToken(jwtToken.getAccessToken())
                .grantType(jwtToken.getGrantType())
                .build();
    }
}