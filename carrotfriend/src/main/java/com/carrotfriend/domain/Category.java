package com.carrotfriend.domain;

import io.swagger.v3.oas.annotations.extensions.Extension;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder @Getter
public class Category {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private String name;

    @OneToMany(mappedBy = "category")
    private List<UserToCategory> userToCategoryList = new ArrayList<>();

}
