package com.carrotfriend.domain;

import com.carrotfriend.dto.user.UserDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String userId;
    @Column
    private String pw;
    @Column
    private String nickName;
    @Column
    private LocalDate birthday;
    @Column
    private String email;

    @OneToMany(mappedBy = "user")
    private List<UserToCategory> userToCategoryList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private List<Post> postList = new ArrayList<>();

    public void addPost(Post post){
        this.postList.add(post);
    }

}
