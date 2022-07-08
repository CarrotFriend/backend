package com.carrotfriend.service;

import com.carrotfriend.domain.User;
import com.carrotfriend.dto.auth.JwtDto;
import com.carrotfriend.dto.auth.LoginDto;
import com.carrotfriend.exception.CookieNotFound;
import com.carrotfriend.exception.UserPasswordNotMatchedException;
import com.carrotfriend.jwt.JwtToken;
import com.carrotfriend.jwt.JwtTokenProvider;
import com.carrotfriend.util.RedisUtil;
import io.netty.util.internal.ObjectUtil;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@RequiredArgsConstructor
@Service
@Transactional
public class AuthService {
    private static Logger logger = LoggerFactory.getLogger(AuthService.class);
    private final UserService userService;
    private final JwtTokenProvider provider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final RedisUtil redisUtil;
    private final PasswordEncoder passwordEncoder;

    public JwtToken logIn(LoginDto loginDto){
        User user = userService.getUserByUserId(loginDto.getUserId());
        if(!passwordEncoder.matches(loginDto.getPw(),user.getPw())) throw new UserPasswordNotMatchedException("비밀번호 매칭 실패");

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(loginDto.getUserId(), loginDto.getPw());
        Authentication auth = authenticationManagerBuilder.getObject().authenticate(token);
        JwtToken jwtToken = provider.createToken(auth);

        redisUtil.set(auth.getName(), jwtToken.getRefreshToken(), jwtToken.getRefreshTokenExpiration().getTime());

        return jwtToken;
    }

    public boolean logOut(Cookie cookie, HttpServletRequest req){
        logger.info("cookie info :: {}",cookie);

        String refresh = cookie.getValue();
        String access = provider.resolveToken(req);
        validate(access, refresh);
        cookie.setMaxAge(0);

        if(!validate(access, refresh))
            return false;
        String token = provider.validateToken(access)? access : refresh;
        Authentication auth = provider.getAuthentication(token);
        redisUtil.delete(auth.getName());
        return true;
    }

    private boolean validate(String accessToken, String refreshToken){
        if(!provider.validateToken(accessToken) && !provider.validateToken(refreshToken))
            return false;
        return true;
    }

    private void validate(String refreshToken){
        if(!provider.validateToken(refreshToken)) throw new RuntimeException();
    }

    public JwtToken regenerator(Cookie cookie) {
        logger.info("cookie info :: {}",cookie);

        String refresh = cookie.getValue();

        validate(refresh);
        logger.info(refresh);
        return provider.createToken(refresh);
    }
}
