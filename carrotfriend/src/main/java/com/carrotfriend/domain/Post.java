package com.carrotfriend.domain;

import com.carrotfriend.dto.post.CreateDto;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode
public class Post {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime regDate;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> imageList = new ArrayList<>();
    @Transient
    private List<String> tagList = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="categoryId")
    private Category category;

    public void setCategory(Category category){
        if(this.category != null){
            this.category.getPostList().remove(this);
        }
        this.category = category;
        category.getPostList().add(this);
    }
    public void setUser(User user){
        this.user = user;
        user.addPost(this);
    }
    public void addImage(Image image){
        this.imageList.add(image);
        image.setPost(this);
    }
    public void addTag(String tag){this.tagList.add(tag);}

    public static Post of(CreateDto createDto){
        Post post = new Post();
        User user = new User();
        user.setId(createDto.getUserId());
        post.setUser(user);
        post.setRegDate(LocalDateTime.now());
        post.setTitle(createDto.getTitle());
        post.setCategory(Category.of(createDto.getCategory()));
        post.setContent(createDto.getContent());
        createDto.getTagList().stream().map(t->t.getText()).forEach(t->post.addTag(t));
        createDto.getImageList().stream().map(i->Image.of(i)).forEach(i->post.addImage(i));
        return post;
    }
}
