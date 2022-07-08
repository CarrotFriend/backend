package com.carrotfriend.domain;

import com.carrotfriend.dto.user.UpdateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder @Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String userId;
    private String pw;
    private String name;
    @Column(unique = true)
    private String nickName;
    private LocalDate birthday;
    @Column(unique = true)
    private String email;
    private LocalDateTime regDate;
    @Enumerated(EnumType.STRING)
    private Role role;
    private Double temperature;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<UserToCategory> userToCategoryList = new ArrayList<>();
    @OneToMany(mappedBy = "user")
    private final List<Post> postList = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name="imageId", nullable = true)
    private Image image;

    public void addPost(Post post) {
        this.postList.add(post);
    }

    public void modifyTemperaure(){
        //need to implementation
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void removeCategory(UserToCategory c) {
        this.userToCategoryList.remove(c);
    }

    public void update(UpdateDto updateDto) {
        this.image = Image.builder().src(updateDto.getImage().getSrc()).build();
        this.pw = updateDto.getPw();
        this.nickName = updateDto.getNickName();
    }
}