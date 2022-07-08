package com.carrotfriend.controller;

import com.carrotfriend.dto.user.*;
import com.carrotfriend.service.UserService;
import com.carrotfriend.util.Response;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Response response;

    @Operation(summary = "유저 조회", description = "아이디로 1명 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@Parameter(description = "id", required = true, example = "username") @PathVariable String userId){
        return response.success(UserDto.of(userService.getUserByUserId(userId)));
    }
    @Operation(summary = "회원 가입", description = "")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto user){
        userService.join(user);
        return response.success("ok");
    }

    @PutMapping("")
    public ResponseEntity<?> modifyUser(@RequestBody UpdateDto updateDto){

        return response.success(UserDto.of(userService.modify(updateDto)));
    }

    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto){
        userService.deleteUser(userDto);
        return response.success("ok");
    }
    @PostMapping("/category")
    public ResponseEntity<?> setCategory(@RequestBody UserCateDto userCateDto){
        userService.setCategory(userCateDto);
        return response.success(Collections.emptyList());
    }
}