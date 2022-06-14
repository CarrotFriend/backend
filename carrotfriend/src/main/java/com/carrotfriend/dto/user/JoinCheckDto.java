package com.carrotfriend.dto.user;

import com.carrotfriend.domain.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Builder
public class JoinCheckDto {
    private List<String> userIdList;
    private List<String> nickNameList;
    private List<String> emailList;

    public static JoinCheckDto of(List<User> userList){
        List<String> userIdList = new ArrayList<>();
        List<String> nickNameList = new ArrayList<>();
        List<String> emailList = new ArrayList<>();

        userList.stream().forEach(u->{
            userIdList.add(u.getUserId());
            nickNameList.add(u.getNickName());
            emailList.add(u.getEmail());
        });

        return JoinCheckDto.builder()
                .userIdList(userIdList)
                .nickNameList(nickNameList)
                .emailList(emailList)
                .build();
    }
}
