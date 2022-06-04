package com.carrotfriend.domain;

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
    private Long code;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<UserToCategory> userToCategoryList = new ArrayList<>();
    @OneToMany(mappedBy = "category")
    private List<Post> postList = new ArrayList<>();
}
