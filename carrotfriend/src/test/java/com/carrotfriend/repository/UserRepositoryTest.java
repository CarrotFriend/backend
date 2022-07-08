package com.carrotfriend.repository;

import com.carrotfriend.domain.Role;
import com.carrotfriend.domain.User;
import com.carrotfriend.exception.UserNotFoundException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {
    @Autowired private UserRepository repository;
    private final Logger log = LoggerFactory.getLogger(UserRepositoryTest.class);

    @BeforeEach
    public void setUp(){
        repository.save(User.builder()
                .userId("test01")
                .pw("test01")
                .role(Role.USER)
                .regDate(LocalDateTime.now())
                .nickName("test01")
                .email("test01")
                .birthday(LocalDate.now())
                .build());

        repository.save(User.builder()
                .userId("test02")
                .pw("test02")
                .role(Role.USER)
                .regDate(LocalDateTime.now())
                .nickName("test02")
                .email("test02")
                .birthday(LocalDate.now())
                .build());

        repository.save(User.builder()
                .userId("admin")
                .pw("admin")
                .role(Role.ADMIN)
                .regDate(LocalDateTime.now())
                .nickName("admin")
                .email("admin")
                .birthday(LocalDate.now())
                .build());
    }

    @Test
    public void save(){
        User user = User.builder()
                .userId("testId")
                .pw("testPw")
                .role(Role.USER)
                .regDate(LocalDateTime.now())
                .nickName("testNick")
                .email("testEmail")
                .birthday(LocalDate.now())
                .build();
        repository.save(user);
        infoUser(user);
    }

    @Test
    public void findById(){
        User user = repository.findById(2L).orElseThrow(()->new UserNotFoundException("존재하지 않는 유저."));
        infoUser(user);
    }
    @Test
    public void findByIdWithException(){
        Assertions.assertThrows(UserNotFoundException.class,()->{
            User user = repository.findById(5L).orElseThrow(()->new UserNotFoundException("존재하지 않는 유저."));
            infoUser(user);
        });
    }

    @Test
    public void findByEmail(){
        User user = repository.findByEmail("test02").orElseThrow(() -> new UserNotFoundException("존재하지 않는 유저입니다."));
        infoUser(user);
    }

    private void infoUser(User user){
        log.info("[user.id]->{}",user.getId());
        log.info("[user.userId]->{}",user.getUserId());
        log.info("[user.pw]->{}",user.getPw());
        log.info("[user.role]->{}",user.getRole());
        log.info("[user.regDate]->{}",user.getRegDate());
        log.info("[user.nickName]->{}",user.getNickName());
        log.info("[user.email]->{}",user.getEmail());
        log.info("[user.birthday]->{}",user.getBirthday());
    }
}