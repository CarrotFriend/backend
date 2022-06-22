package com.carrotfriend.service;

import com.carrotfriend.domain.*;
import com.carrotfriend.repository.PostRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;


@Rollback
@DataJpaTest
class PostServiceTest {
    @Autowired
    private PostRepository postRepository;

    @Test
    void save() {
        User user = User.builder().userId("testUser").build();
        Image image = Image.builder().src("testImg").build();
        Category category = Category.builder().name("testCategory").build();
        Post post = Post.builder()
                .title("test01")
                .content("test content")
                .user(user)
                .category(category)
                .regDate(LocalDateTime.now())
                .imageList(new ArrayList<>())
                .build();
        post.addImage(image);

        Post save = postRepository.save(post);

    }

    @Test
    void findAllWithCategory() {
    }

    @Test
    void getPostList() {
    }

    @Test
    void update() {
    }

    @Test
    void getPostById() {
    }
}