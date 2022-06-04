package com.carrotfriend.controller;

import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.dto.user.JoinDto;
import com.carrotfriend.dto.user.UserCateDto;
import com.carrotfriend.dto.user.UserDto;
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

//    @Operation(summary = "유저 조회", description = "아이디로 1명 조회")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> findUserById(@Parameter(description = "id", required = true, example = "1") @PathVariable Long id){
//        return response.success(UserDto.of(userService.findById(id)));
//    }
    @Operation(summary = "유저 조회", description = "아이디로 1명 조회")
    @GetMapping("/{userId}")
    public ResponseEntity<?> findUserById(@Parameter(description = "id", required = true, example = "username") @PathVariable String userId){
        return response.success(UserDto.of(userService.findByUserId(userId)));
    }
    @Operation(summary = "회원 가입", description = "")
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody JoinDto user){
        return response.success(userService.join(user));
    }

    @DeleteMapping("/")
    public ResponseEntity<?> deleteUser(@RequestBody UserDto userDto){
        return response.success(userService.deleteUser(userDto));
    }
    @PostMapping("/category")
    public ResponseEntity<?> addCategory(@RequestBody UserCateDto userCateDto){
        userService.insertCategory(userCateDto.getUserDto(),userCateDto.getCategoryDto());
        return response.success(Collections.emptyList());
    }
    @DeleteMapping("/category")
    public ResponseEntity<?> deleteCategory(@RequestBody UserCateDto userCateDto){
        return response.success(userService.deleteCategory(userCateDto.getUserDto(),userCateDto.getCategoryDto()));
    }
}
