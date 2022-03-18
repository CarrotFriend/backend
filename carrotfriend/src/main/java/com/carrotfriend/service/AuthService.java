package com.carrotfriend.service;

import com.carrotfriend.domain.User;
import com.carrotfriend.dto.auth.JwtDto;
import com.carrotfriend.dto.auth.LoginDto;
import com.carrotfriend.dto.auth.LogoutDto;
import com.carrotfriend.dto.auth.NewTokenDto;
import com.carrotfriend.jwt.JwtToken;
import com.carrotfriend.jwt.JwtTokenProvider;
import com.carrotfriend.util.RedisUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Service
public class AuthService {
    private final UserService userService;
    private final JwtTokenProvider provider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;
    public JwtDto logIn(LoginDto loginDto){
        User user = userService.findByUserId(loginDto.getUserId());

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPw());
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(token);
        JwtToken jwtToken = provider.createToken(auth);

        redisUtil.set(auth.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpiration().getTime());

        return JwtDto.of(jwtToken);
    }
    public boolean logOut(LogoutDto logoutDto){
        if(!validate(logoutDto.getAccessToken(), logoutDto.getRefreshToken()))
            return false;
        String token = provider.validateToken(logoutDto.getAccessToken())? logoutDto.getAccessToken() : logoutDto.getRefreshToken();
        Authentication auth = provider.getAuthentication(token);
        redisUtil.delete(auth.getName());
        return true;
    }
    private boolean validate(String accessToken, String refreshToken){
        if(!provider.validateToken(accessToken) && !provider.validateToken(refreshToken))
            return false;
        return true;
    }
    public JwtDto newJwt(NewTokenDto newTokenDto){
        if(!validate(newTokenDto.getAccessToken(), newTokenDto.getRefreshToken()))
            return null;
        return null;
    }


}
