package com.carrotfriend.controller;

import com.carrotfriend.dto.auth.LoginDto;
import com.carrotfriend.dto.auth.LogoutDto;
import com.carrotfriend.service.AuthService;
import com.carrotfriend.util.Response;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;
    private final Response response;

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@RequestBody LoginDto loginDto){
        return response.success(authService.logIn(loginDto));
    }

    @PostMapping("/token")
    public ResponseEntity<?> generateToken(){
        return null;
    }

    @PostMapping("/logout")
    @Parameter(in = ParameterIn.HEADER,  required = true, name = "Authorization", description = "Access Token")
    public ResponseEntity<?> logOut(@RequestBody LogoutDto logoutDto){
        return response.success(authService.logOut(logoutDto)?"success":"failed");
    }
}
