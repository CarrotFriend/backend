package com.carrotfriend.service;

import com.carrotfriend.domain.Image;
import com.carrotfriend.domain.Post;
import com.carrotfriend.dto.post.CreateDto;
import com.carrotfriend.dto.post.PostDto;
import com.carrotfriend.dto.post.UpdateDto;
import com.carrotfriend.repository.ImageRepository;
import com.carrotfriend.repository.PostRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PostService {
    private final int cnt = 10;
    private final PostRepository postRepository;
    private final ImageRepository imageRepository;
    private final UserService userService;

    public PostDto save(CreateDto createDto){
        Post post = Post.toEntity(createDto);
        createDto.getImageList().forEach(i -> {
            Image image = Image.toEntity(i);
            image.setPost(post);
            imageRepository.save(image);
        });

        post.setUser(userService.findByUserId(post.getUser().getUserId()));
        return PostDto.of(postRepository.save(post));
    }
    public List<PostDto> getPostList(int offset){
        List<Post> posts = postRepository.findAll();
        List<PostDto> postDtoList = new ArrayList<>();
        for(int i = offset; i< offset+20; i++){
            if(posts.size() <= i)break;
            postDtoList.add(PostDto.of(posts.get(i)));
        }
        return postDtoList;
    }
    public Post update(UpdateDto updateDto){
        Post post = getPostById(updateDto.getId());
        post.setContent(updateDto.getContent());
        post.setTitle(updateDto.getTitle());
        return post;
    }

    public Post getPostById(Long id){
        Post post = postRepository.findById(id).orElseGet(null);

        if(post == null) throw new RuntimeException("Not found post by id : "+id);
        return post;
    }
}
