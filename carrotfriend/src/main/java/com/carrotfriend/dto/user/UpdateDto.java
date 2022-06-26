package com.carrotfriend.dto.user;

import com.carrotfriend.dto.post.ImageDto;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class UpdateDto {
    private Long id;
    private String pw;
    private String nickName;
    private ImageDto image;

    public void setPw(String pw) {
        this.pw = pw;
    }
}
