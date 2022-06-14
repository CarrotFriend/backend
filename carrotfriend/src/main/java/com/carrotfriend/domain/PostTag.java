package com.carrotfriend.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Setter
public class PostTag {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="tagId")
    private Tag tag;

    public static PostTag createPostTag(Post post, Tag tag){
        PostTag postTag = new PostTag();
        postTag.setPost(post);
        post.addPostTag(postTag);
        postTag.setTag(tag);
        tag.addPostTag(postTag);
        return postTag;
    }

}
