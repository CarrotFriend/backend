package com.carrotfriend.service;

import com.carrotfriend.domain.Category;
import com.carrotfriend.domain.Image;
import com.carrotfriend.domain.Post;
import com.carrotfriend.dto.post.CreateDto;
import com.carrotfriend.dto.post.PostDto;
import com.carrotfriend.dto.post.TagDto;
import com.carrotfriend.dto.post.UpdateDto;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.repository.ImageRepository;
import com.carrotfriend.repository.PostRepository;
import com.carrotfriend.util.RedisUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PostService {

    @PersistenceContext
    private final EntityManager em;
    private final PostRepository postRepository;
    private final CategoryService categoryService;
    private final ImageRepository imageRepository;
    private final UserService userService;
    private final RedisUtil redisUtil;
    @Transactional
    public Post save(Post post){
        post.setUser(userService.getUserById(post.getUser().getId()));
        post = postRepository.save(post);


        redisUtil.set("POST_VIEW::"+post.getId(), 0, Long.MAX_VALUE);
        redisUtil.set("POST_TAG::"+post.getId(), post.getTagList(),Long.MAX_VALUE);

        return post;
    }
    @Transactional
    public void remove(Long id){
        Post post = getPostById(id);
        redisUtil.delete("POST_VIEW::"+post.getId());
        redisUtil.delete("POST_TAG::"+post.getId());
        postRepository.delete(post);
    }
    public List<PostDto> findAllWithCategory(Long id){
        List<Post> postList = categoryService.getOneById(id).getPostList();
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            post.setTagList((List<String>)redisUtil.get("POST_TAG::"+post.getId()));
            PostDto postDto = PostDto.of(post);
            postDto.setView((int)redisUtil.get("POST_VIEW::"+post.getId()));
        }
        return postDtoList;
    }
    public Post read(Long id){
        Post post = getPostById(id);
        redisUtil.set("POST_VIEW::"+post.getId(), (int)redisUtil.get("POST_VIEW::"+post.getId())+1, Long.MAX_VALUE);
        return post;
    }

    @Transactional
    public Post update(UpdateDto updateDto){
        Post post = getPostById(updateDto.getId());
        post.setCategory(categoryService.getOneById(updateDto.getCategory().getId()));
        post.setContent(updateDto.getContent());
        post.setTitle(updateDto.getTitle());
        post.setTagList(updateDto.getTagList().stream().map(t->t.getText()).collect(Collectors.toList()));
        post.setImageList(updateDto.getImageList().stream().map(i->Image.of(i)).collect(Collectors.toList()));

        redisUtil.set("POST_TAG::"+post.getId(), post.getTagList(),Long.MAX_VALUE);
        return post;
    }

    public Post getPostById(Long id){
        Post post = em.createQuery("select p from Post p" +
                " inner join fetch p.user u" +
                " inner join fetch p.category c"+
                " left join fetch p.imageList i where p.id=:id", Post.class).setParameter("id", id).getSingleResult();
        post.setTagList((List<String>)redisUtil.get("POST_TAG::"+post.getId()));
        return post;
    }
}
