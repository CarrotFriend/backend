package com.carrotfriend.service;

import com.carrotfriend.domain.Post;
import com.carrotfriend.domain.User;
import com.carrotfriend.domain.UserToCategory;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.dto.user.JoinDto;
import com.carrotfriend.dto.user.UserDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@Transactional
class UserServiceTest {
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @BeforeEach
    void setUp(){
        int num =1;
        categoryService.createCategory(CategoryDto.builder()
                .name("test01").build());
        categoryService.createCategory(CategoryDto.builder()
                .name("test02").build());
        categoryService.createCategory(CategoryDto.builder()
                .name("test03").build());
        List<CategoryDto> list = categoryService.findAll();
        for(int i=0; i<5; i++) {
            String test = "test" + num++;
            JoinDto joinDto = JoinDto.builder()
                    .userId(test)
                    .pw(test)
                    .nickName(test)
                    .email(test)
                    .birthday(LocalDate.now())
                    .build();
            UserDto of = UserDto.of(userService.join(joinDto));
            of.setCategoryList(list);
//            userService.setCategory(of);
        }
    }
    @Test
    void join() {
        JoinDto joinDto = JoinDto.builder()
                .userId("testId")
                .pw("testPw")
                .nickName("testNick")
                .email("testEmail")
                .birthday(LocalDate.now())
                .build();
        User res = userService.join(joinDto);

//        Assertions.assertEquals(1L, res.getId());
    }

    @Test
    void deleteUser() {
    }


    @Test
    void 카테고리_중복_추가(){
        //기존 카테고리 1,2,3 에서 중복되는 1 추가
//         CategoryDto categoryDto = CategoryDto.builder().categoryId(1L).name("1").build();
         User user1 = userService.getUserById(1L);
         UserDto of = UserDto.of(user1);
//         of.getCategoryList().add(categoryDto);
//         userService.setCategory(of);

        User user2 = userService.getUserById(1L);
        //카테고리 사이즈 같음 비교
        Assertions.assertEquals(user1.getUserToCategoryList().size(),user2.getUserToCategoryList().size());
    }
    @Test
    void 카테고리_추가(){
        // 카테고리 4 추가
//        CategoryDto categoryDto = CategoryDto.builder().categoryId(4L).name("4").build();
//        categoryService.createCategory(categoryDto);

        UserDto of = UserDto.of(userService.getUserById(1L));
//        of.getCategoryList().add(categoryDto);
//        userService.setCategory(of);

        User user2 = userService.getUserById(1L);
        //카테고리 사이즈 4 비교
        Assertions.assertEquals(4,user2.getUserToCategoryList().size());
    }
    @Test
    void 카테고리_삭제(){
        // 카테고리 2, 3삭제
        UserDto of = UserDto.of(userService.getUserById(1L));
        of.getCategoryList().remove(1);
        of.getCategoryList().remove(1);
//        userService.setCategory(of);

        User user2 = userService.getUserById(1L);
        //카테고리 사이즈 1 비교
        Assertions.assertEquals(1,user2.getUserToCategoryList().size());
    }

    @Test
    void getUserById() {
        User user = userService.getUserById(1L);

        printDetail(user);
    }
    private void printDetail(User user){
        System.out.println("userId : " +user.getId());
        System.out.println("userUserId : " +user.getUserId());
        System.out.println("useEmail : " +user.getEmail());
        System.out.println("useNickName : " +user.getNickName());
        System.out.println("useRole : " +user.getRole());
        System.out.println("userRegDate : " +user.getRegDate());
        System.out.println("userBRD : " +user.getBirthday());
        System.out.println("====================================");
        System.out.println("userImage : " +user.getImage());
        System.out.println("userPost : ");
        for(Post post : user.getPostList()) {
            System.out.println("    postId : " + post.getId());
            System.out.println("    postTitle : " + post.getTitle());
        }
        System.out.println("userCategory : ");
        for(UserToCategory utc : user.getUserToCategoryList()){
            System.out.println("    utcId : "+utc.getId());
            System.out.println("    utcCatId : "+utc.getCategory().getId());
        }
        System.out.println("====================================");
    }
    @Test
    void getUserByUserId() {

    }
    @Test
    void getUserByEmail(){

    }
}