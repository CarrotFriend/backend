package com.carrotfriend.util;

import com.carrotfriend.domain.Gender;
import com.carrotfriend.domain.User;
import com.carrotfriend.dto.UserRequestDto;
import com.carrotfriend.dto.UserResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {
    @Test
    public void userTest1(){
        UserRequestDto.User userRequest = UserRequestDto.User.builder()
                .userID("hong123")
                .pw("1234")
                .nickName("garoo")
                .gender(Gender.MAN)
                .birthday(LocalDateTime.now())
                .email("hong123@hong.com").build();

        User user = User.builder()
                .id(1L)
                .userID(userRequest.getUserID())
                .pw(userRequest.getPw())
                .nickName(userRequest.getNickName())
                .gender(userRequest.getGender())
                .birthday(userRequest.getBirthday())
                .email(userRequest.getEmail())
                .build();

        UserResponseDto.User userResponse = UserResponseDto.User.builder()
                .id(user.getId())
                .userID(user.getUserID())
                .nickName(user.getNickName())
                .gender(user.getGender())
                .birthday(user.getBirthday())
                .email(user.getEmail())
                .build();
        Response response = new Response();
        ResponseEntity<?> success = response.success(userResponse);
        System.out.println(success);
    }

}