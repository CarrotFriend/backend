package com.carrotfriend.domain;

import com.carrotfriend.dto.post.CreateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDate regDate;
    private int views;

    @OneToMany(mappedBy = "post")
    private List<Image> imageList = new ArrayList<>();
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryCode")
    private Category category;


    public void setCategory(Category category){
        this.category = category;
        category.getPostList().add(this);
    }
    public void setUser(User user){
        this.user = user;
        user.addPost(this);
    }
    public void addImage(Image image){
        this.imageList.add(image);
    }
    public void increaseViews(){
        this.views++;
    }

    public static Post toEntity(CreateDto createDto){
        Post post = Post.builder()
                .title(createDto.getTitle())
                .content(createDto.getContent())
                .regDate(LocalDate.now())
                .imageList(Collections.emptyList())
                .views(0)
                .user(User.builder().userId(createDto.getUserId()).build())
                .build();
        createDto.getImageList().stream().forEach(c->post.getImageList().add(Image.toEntity(c)));
        return post;
    }
}
