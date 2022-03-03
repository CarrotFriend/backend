package com.carrotfriend.dto;

import com.carrotfriend.domain.Category;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

public class UserResponseDto {
    @Builder
    @ToString
    public static class User {
        private Long id;
        private String userID;
        private String nickName;
        private List<Category> categoryList = new ArrayList<>();

        public void setCategory(Category category){
            categoryList.add(category);
        }
    }
}
