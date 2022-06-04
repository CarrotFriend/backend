package com.carrotfriend.service;

import com.carrotfriend.domain.User;
import com.carrotfriend.dto.user.JoinDto;
import com.carrotfriend.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Test
    void join() {
        JoinDto joinDto = JoinDto.builder()
                .userId("testId")
                .pw("testPw")
                .nickName("testNick")
                .email("testEmail")
                .birthday(LocalDate.now())
                .build();
        userService.join(joinDto);
    }

    @Test
    void insertCategory() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void deleteCategory() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByUserId() {
    }
}