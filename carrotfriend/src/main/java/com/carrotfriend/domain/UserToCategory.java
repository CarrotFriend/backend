package com.carrotfriend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@Setter@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserToCategory {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryCode")
    private Category category;

    public static UserToCategory createRelWithUserAndCat(User user, Category category){
        UserToCategory userToCategory = new UserToCategory();
        userToCategory.setUser(user);
        userToCategory.setCategory(category);
        return userToCategory;
    }

    public void setUser(User user){
        this.user = user;
        user.getUserToCategoryList().add(this);
    }

    public void setCategory(Category category){
        this.category = category;
        category.getUserToCategoryList().add(this);
    }
}