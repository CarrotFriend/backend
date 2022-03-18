package com.carrotfriend.service;

import com.carrotfriend.domain.Category;
import com.carrotfriend.domain.User;
import com.carrotfriend.domain.UserToCategory;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.dto.user.JoinDto;
import com.carrotfriend.dto.user.UserDto;
import com.carrotfriend.repository.CategoryRepository;
import com.carrotfriend.repository.UserRepository;
import com.carrotfriend.repository.UserToCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserToCategoryRepository userToCategoryRepository;

    public UserDto join(JoinDto joinUser){
        User user = userRepository.save(com.carrotfriend.domain.User.builder()
                .userId(joinUser.getUserId())
                .pw(passwordEncoder.encode(joinUser.getPw()))
                .nickName(joinUser.getNickName())
                .email(joinUser.getEmail())
                .birthday(joinUser.getBirthday())
                .userToCategoryList(Collections.emptyList())
                .build());
        return UserDto.of(user);
    }
    public UserDto insertCategory(UserDto userDto, CategoryDto categoryDto){
        User user = findByUserId(userDto.getUserId());
        Category category = categoryRepository.findCategoryByCodeAndName(categoryDto.getCode(), categoryDto.getName()).orElseGet(null);
        UserToCategory userToCategory = UserToCategory.createRelWithUserAndCat(user, category);
        userToCategoryRepository.save(userToCategory);
        return UserDto.of(user);
    }
    public boolean deleteUser(UserDto userDto){
        userRepository.delete(findByUserId(userDto.getUserId()));
        return true;
    }
    public UserDto deleteCategory(UserDto userDto, CategoryDto categoryDto){
        User user = findByUserId(userDto.getUserId());
        List<UserToCategory> userToCategoryList = user.getUserToCategoryList();
        for(UserToCategory userToCategory : userToCategoryList){
            Category category = userToCategory.getCategory();
            if(category.getCode().equals(categoryDto.getCode())
            && category.getName().equals(categoryDto.getName())){
                userToCategoryRepository.delete(userToCategory);
                break;
            }
        }
        return UserDto.of(findByUserId(userDto.getUserId()));
    }
    public User findById(Long id){
        User user = userRepository.findById(id).orElseGet(null);
        if(user == null) throw new UsernameNotFoundException("User Id : "+id+" not exist");
        return user;
    }
    public User findByUserId(String userId){
        User user = userRepository.findByUserId(userId).orElse(null);
        if(user == null) throw new UsernameNotFoundException("Not Found this Id");
        return user;
    }
}
