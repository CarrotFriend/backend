package com.carrotfriend.domain;

import com.carrotfriend.dto.post.ImageDto;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imageName;
    private String locate;
    private Long fileSize;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="postId")
    private Post post;

    public void setPost(Post post){
        this.post = post;
        post.addImage(this);
    }

    public static Image toEntity(ImageDto imageDto){
        return Image.builder()
                .imageName(imageDto.getImageName())
                .fileSize(imageDto.getFileSize())
                .locate(imageDto.getLocate())
                .build();
    }
}
