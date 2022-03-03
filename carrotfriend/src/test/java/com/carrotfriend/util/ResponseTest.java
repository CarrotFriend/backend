package com.carrotfriend.util;

import com.carrotfriend.domain.Gender;
import com.carrotfriend.domain.User;
import com.carrotfriend.dto.UserRequestDto;
import com.carrotfriend.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

class ResponseTest {
    @Test
    public void userTest1(){
        UserRequestDto.JoinUser joinUserRequest = UserRequestDto.JoinUser.builder()
                .userId("hong123")
                .pw("1234")
                .nickName("garoo")
                .gender(Gender.MAN)
                .birthday(LocalDateTime.now())
                .email("hong123@hong.com").build();

        User user = com.carrotfriend.domain.User.builder()
                .id(1L)
                .userID(joinUserRequest.getUserId())
                .pw(joinUserRequest.getPw())
                .nickName(joinUserRequest.getNickName())
                .gender(joinUserRequest.getGender())
                .birthday(joinUserRequest.getBirthday())
                .email(joinUserRequest.getEmail())
                .build();

        UserResponseDto.User userResponse = UserResponseDto.User.builder()
                .id(user.getId())
                .userId(user.getUserId())
                .nickName(user.getNickName())
                .build();
        Response response = new Response();
        ResponseEntity<?> success = response.success(userResponse);
        System.out.println(success);
    }

}