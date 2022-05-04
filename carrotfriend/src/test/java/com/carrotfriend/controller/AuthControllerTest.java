package com.carrotfriend.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import static org.junit.jupiter.api.Assertions.*;

@EnableJpaRepositories
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private AuthController authController;

    @Test
    public void test() {

    }
}