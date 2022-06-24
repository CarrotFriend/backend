package com.carrotfriend.domain;

import com.carrotfriend.dto.user.CategoryDto;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
//@Table(name = "CATEGORIES")
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<UserToCategory> userToCategoryList = new ArrayList<>();
    @OneToMany(mappedBy = "category")
    private final List<Post> postList = new ArrayList<>();

    public static Category of(CategoryDto categoryDto){
        Category category = new Category(categoryDto.getCategoryId(), categoryDto.getName());
        return category;
    }
}
