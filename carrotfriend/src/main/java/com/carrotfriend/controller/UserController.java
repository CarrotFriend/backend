package com.carrotfriend.controller;

import com.carrotfriend.dto.UserRequestDto;
import com.carrotfriend.dto.UserResponseDto;
import com.carrotfriend.service.UserService;
import com.carrotfriend.util.Response;
import io.lettuce.core.dynamic.annotation.Param;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final Response response;

    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @GetMapping("/")
    public ResponseEntity<?> findUserById(@Parameter(description = "id", required = true, example = "1") @RequestParam("id") Long id){
        return response.success(userService.findById(id));
    }
    @Operation(summary = "", description = "")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestBody UserRequestDto.JoinUser user){
        return response.success(userService.join(user));
    }

}
