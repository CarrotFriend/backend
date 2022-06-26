package com.carrotfriend.controller;

import com.carrotfriend.domain.Post;
import com.carrotfriend.dto.post.CreateDto;
import com.carrotfriend.dto.post.PostDto;
import com.carrotfriend.service.PostService;
import com.carrotfriend.util.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/post")
public class PostController {
    private final PostService postService;
    private final Response response;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody CreateDto createDto){
        return response.success(PostDto.of(postService.save(Post.of(createDto))));
    }
    @GetMapping("/category/{id}")
    public ResponseEntity<?> findAllWithCategory(@PathVariable Long id){
        return response.success(postService.findAllWithCategory(id));
    }
    @GetMapping("/mypost/{id}")
    public ResponseEntity<?> getMyPosts(@PathVariable Long id){
        return response.success(postService.getMyPosts(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        return null;
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(){
        return null;
    }


    @GetMapping("/detail/{id}")
    public ResponseEntity<?> read(@PathVariable Long id){
        return response.success(PostDto.of(postService.read(id)));
    }
}
