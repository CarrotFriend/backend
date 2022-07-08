package com.carrotfriend.controller;

import com.carrotfriend.dto.auth.JwtDto;
import com.carrotfriend.dto.auth.LoginDto;
import com.carrotfriend.jwt.JwtToken;
import com.carrotfriend.service.AuthService;
import com.carrotfriend.util.Response;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;
    private final Response response;
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto, HttpServletResponse resp){
        JwtToken jwtToken = authService.logIn(loginDto);

        ResponseCookie cookie = ResponseCookie.from("refresh-token", jwtToken.getRefreshToken())
                .maxAge(60*60*24*15)
                .httpOnly(true)
                .secure(true)
                .domain("carrot-back.herokuapp.com")
                .path("/")
                .sameSite("None")
                .build();
        resp.setHeader("Set-Cookie", cookie.toString());
        return response.success(JwtDto.of(jwtToken));
    }

    @GetMapping("/reissue")
    public ResponseEntity<?> generateToken(@CookieValue(value = "refresh-token", required = true) Cookie cookie){
        return response.success(JwtDto.of(authService.regenerator(cookie)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(@CookieValue(value = "refresh-token", required = true) Cookie cookie, HttpServletRequest req){
        authService.logOut(cookie, req);
        return response.success("ok");
    }
}