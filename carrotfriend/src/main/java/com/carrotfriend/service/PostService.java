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
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PostService {
    private static Logger logger = LoggerFactory.getLogger(PostService.class);
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

        Map<String, Object> map = new HashMap<>();
        map.put("view", 0);
        map.put("tag", post.getTagList());

        redisUtil.set("POST_INFO::"+post.getId(), map, Long.MAX_VALUE/ 1000L);

        return post;
    }
    @Transactional
    public void remove(Long id){
        Post post = getPostById(id);
        redisUtil.delete("POST_INFO::"+post.getId());
        postRepository.delete(post);
    }
    public List<PostDto> findAllWithCategory(Long id){
        List<Post> postList = categoryService.getOneById(id).getPostList();
        List<PostDto> postDtoList = new ArrayList<>();
        for(Post post : postList){
            Map<String,Object> map = (Map<String,Object>)redisUtil.get("POST_INFO::" + post.getId());
            ObjectMapper mapper = new ObjectMapper();

            Integer view = mapper.convertValue(map.get("view"), Integer.class);
            List<String> tag = mapper.convertValue(map.get("tag"), new TypeReference<List<String>>() {});

            post.setTagList(tag);
            PostDto postDto = PostDto.of(post);
            postDto.setView(view);
            postDtoList.add(postDto);
        }
        return postDtoList;

    }
    public PostDto read(Long id){
        Post post = getPostById(id);
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = (Map<String, Object>) redisUtil.get("POST_INFO::" + id);
        Integer view = mapper.convertValue(map.get("view"), Integer.class)+1;

        map.put("view", view);
        redisUtil.set("POST_INFO::"+id, map, Long.MAX_VALUE/ 1000L);

        PostDto postDto = PostDto.of(post);
        postDto.setView(view);

        return postDto;
    }

    @Transactional
    public Post update(UpdateDto updateDto){
        Post post = getPostById(updateDto.getId());
        post.setCategory(categoryService.getOneById(updateDto.getCategory().getCode()));
        post.setContent(updateDto.getContent());
        post.setTitle(updateDto.getTitle());
        post.setTagList(updateDto.getTagList().stream().map(t->t.getText()).collect(Collectors.toList()));
        post.setImageList(updateDto.getImageList().stream().map(i->Image.of(i)).collect(Collectors.toList()));



        Map<String, Object> map = (Map<String, Object>) redisUtil.get("POST_INFO::" + post.getId());
        map.put("tag", post.getTagList());
        redisUtil.set("POST_INFO::"+post.getId(), map, Long.MAX_VALUE / 1000L);

        return post;
    }

    public Post getPostById(Long id){
        Post post = em.createQuery("select p from Post p" +
                " inner join fetch p.user u" +
                " inner join fetch p.category c"+
                " left join fetch p.imageList i where p.id=:id", Post.class).setParameter("id", id).getSingleResult();

        ObjectMapper mapper = new ObjectMapper();
        List<String> tag = mapper.convertValue(((Map<String,Object>) redisUtil.get("POST_INFO::"+id))
                .get("tag"), new TypeReference<List<String>>() {});

        post.setTagList(tag);
        return post;
    }

    public List<PostDto> getMyPosts(Long id) {
        List<Post> posts = em.createQuery(
                        "select p from Post p " +
                                "inner join fetch p.category c "+
                                "left join fetch p.imageList i "+
                                "where p.user.id=:id", Post.class)
                .setParameter("id", id).getResultList();
        return posts.stream().
                map(p->{
                    ObjectMapper mapper = new ObjectMapper();
                    List<String> tag = mapper.convertValue(((Map<String,Object>) redisUtil.get("POST_INFO::"+id))
                            .get("tag"), new TypeReference<List<String>>() {});

                    p.setTagList(tag);
                    PostDto dto = PostDto.of(p);
                    return dto;
                }).collect(Collectors.toList());
    }

}
