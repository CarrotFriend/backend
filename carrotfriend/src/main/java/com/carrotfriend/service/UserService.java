package com.carrotfriend.service;

import com.carrotfriend.domain.Role;
import com.carrotfriend.domain.User;
import com.carrotfriend.domain.UserToCategory;
import com.carrotfriend.dto.user.*;
import com.carrotfriend.repository.UserRepository;
import com.carrotfriend.repository.UserToCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    @PersistenceContext
    private final EntityManager em;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CategoryService categoryService;
    private final UserToCategoryRepository userToCategoryRepository;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);

    public User join(JoinDto joinUser){
        logger.info("[UserService] join called");

        User user = userRepository.save(User.builder()
                .userId(joinUser.getUserId())
                .pw(passwordEncoder.encode(joinUser.getPw()))
                .nickName(joinUser.getNickName())
                .name(joinUser.getUserName())
                .email(joinUser.getEmail())
                .role(Role.USER)
                .regDate(LocalDateTime.now())
                .image(null)
                .temperature(36.5)
                .birthday(joinUser.getBirthday())
                .build());
        return user;
    }

    public void setCategory(UserCateDto usercateDto) {
        User user = getUserById(usercateDto.getId());
        List<UserToCategory> willRemove = new ArrayList<>();
        List<UserToCategory> willAdd = new ArrayList<>();

        user.getUserToCategoryList().forEach(c->{
            if(!usercateDto.getCategoryList().contains(CategoryDto.of(c.getCategory()))) willRemove.add(c);
            else usercateDto.getCategoryList().remove(CategoryDto.of(c.getCategory()));
        });

        categoryService.getCategoriesById(usercateDto.getCategoryList().stream().map(c->c.getCode()).collect(Collectors.toList()))
                .forEach(c->willAdd.add(UserToCategory.createRelWithUserAndCat(user, c)));

        logger.info("willRemove : {}",willRemove);
        logger.info("willAdd : {}",willAdd);

        willRemove.forEach(c->user.removeCategory(c));
        userToCategoryRepository.saveAll(willAdd);
    }

    public boolean deleteUser(UserDto userDto){
        userRepository.delete(getUserById(userDto.getId()));
        return true;
    }

    public User getUserById(Long id){
        return em.createQuery("select distinct u from User u left join fetch u.userToCategoryList where u.id =:id", User.class)
                .setParameter("id", id)
                .getSingleResult();
    }

    public User getUserByUserId(String userId){
        return em.createQuery("select distinct u from User u left join fetch u.userToCategoryList where u.userId =:userId", User.class)
                .setParameter("userId", userId)
                .getSingleResult();
    }

    public User getUserByEmail(String email){
        return em.createQuery("select distinct u from User u left join fetch u.userToCategoryList where u.email =:email", User.class)
                .setParameter("email", email)
                .getSingleResult();
    }

    public void checkNickname(String nickname) {
        em.createQuery("select count(u) from User u where u.nickName =:nickname", Long.class)
                .setParameter("nickname", nickname).getSingleResult();
    }

    public void checkEmail(String email) {
        em.createQuery("select count(u) from User u where u.email =:email", Long.class)
                .setParameter("email", email).getSingleResult();
    }

    public void checkUserId(String userId) {
        em.createQuery("select count(u) from User u where u.userId =:userId", Long.class)
                .setParameter("userId", userId).getSingleResult();
    }

    public User modify(UpdateDto updateDto) {
        User user = getUserById(updateDto.getId());
        updateDto.setPw(passwordEncoder.encode(updateDto.getPw()));
        user.update(updateDto);

        return user;
    }
}
