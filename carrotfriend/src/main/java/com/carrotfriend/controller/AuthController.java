package com.carrotfriend.controller;

import com.carrotfriend.dto.auth.JwtDto;
import com.carrotfriend.dto.auth.LoginDto;
import com.carrotfriend.jwt.JwtToken;
import com.carrotfriend.service.AuthService;
import com.carrotfriend.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Response response;
    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto, HttpServletResponse resp){
        JwtToken jwtToken = authService.logIn(loginDto);

        Cookie cookie = new Cookie("userid", loginDto.getUserId());

        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setValue(jwtToken.getRefreshToken());
        resp.addCookie(cookie);

        return response.success(JwtDto.of(jwtToken));
    }

    @PostMapping("/reissue")
    public ResponseEntity<?> generateToken(HttpServletRequest req){
        return response.success(JwtDto.of(authService.regenerator(req)));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logOut(HttpServletRequest req){
        authService.logOut(req);
        return response.success("ok");
    }
}
