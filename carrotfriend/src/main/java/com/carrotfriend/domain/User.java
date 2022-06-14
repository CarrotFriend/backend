package com.carrotfriend.domain;

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
    @Column(unique = true)
    private String nickName;
    private LocalDate birthday;
    @Column(unique = true)
    private String email;
    private LocalDateTime regDate;
    @Enumerated(EnumType.STRING)
    private Role role;

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

    public void removeCategory(UserToCategory c) {
        this.userToCategoryList.remove(c);
    }
}
