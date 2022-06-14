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
        Tag tag = Tag.builder().postTags(new ArrayList<>()).text("testTag").build();
        Category category = Category.builder().name("testCategory").build();
        Post post = Post.builder()
                .title("test01")
                .content("test content")
                .user(user)
                .category(category)
                .regDate(LocalDate.now())
                .imageList(new ArrayList<>())
                .postTagList(new ArrayList<>())
                .views(0)
                .build();
        post.addImage(image);
        PostTag postTag = PostTag.createPostTag(post,tag);

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