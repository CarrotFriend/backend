package com.carrotfriend.controller;

import com.carrotfriend.service.UserService;
import com.carrotfriend.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/verify")
public class VerifyController {
    private final Response response;
    private final UserService userService;

    @GetMapping("/userid/{userId}")
    public ResponseEntity<?> checkUserId(@PathVariable String userId){
        return response.success(isDuplicated(userService.checkUserId(userId)));
    }
    @GetMapping("/email/{email}")
    public ResponseEntity<?> checkEmail(@PathVariable String email){
        return response.success(isDuplicated(userService.checkEmail(email)));
    }
    @GetMapping("/nickname/{nickname}")
    public ResponseEntity<?> checkNickname(@PathVariable String nickname){
        return response.success(isDuplicated(userService.checkNickname(nickname)));
    }

    private Map<String, Integer> isDuplicated(boolean flag){
        Map<String, Integer> data = new HashMap<>();
        data.put("code",flag?200:400);
        return data;
    }
}
