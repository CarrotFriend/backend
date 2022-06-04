package com.carrotfriend.service;

import com.carrotfriend.domain.Category;
import com.carrotfriend.domain.User;
import com.carrotfriend.domain.UserToCategory;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.dto.user.JoinDto;
import com.carrotfriend.dto.user.UserDto;
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
    private final CategoryService categoryService;
    private final PasswordEncoder passwordEncoder;
    private final UserToCategoryRepository userToCategoryRepository;

    public User join(JoinDto joinUser){
        User user = userRepository.save(User.builder()
                .userId(joinUser.getUserId())
                .pw(passwordEncoder.encode(joinUser.getPw()))
                .nickName(joinUser.getNickName())
                .email(joinUser.getEmail())
                .birthday(joinUser.getBirthday())
                .userToCategoryList(Collections.emptyList())
                .build());
        return user;
    }

    public void insertCategory(UserDto userDto, List<CategoryDto> categoryDtos) {
        User user = findById(userDto.getId());
        categoryDtos.forEach(categoryDto->{
            Category category = categoryService.findCategoryByCodeAndName(categoryDto.getCode(), categoryDto.getName());
            userToCategoryRepository.save(UserToCategory.createRelWithUserAndCat(user, category));
        });
    }

    public boolean deleteUser(UserDto userDto){
        userRepository.delete(findByUserId(userDto.getUserId()));
        return true;
    }

    public UserDto deleteCategory(UserDto userDto, List<CategoryDto> categoryDtos){
        User user = findById(userDto.getId());
        categoryDtos.forEach(categoryDto->{
            Category category = categoryService.findOneByCode(categoryDto.getCode());
            userToCategoryRepository.deleteUserToCategoryByUserIdAndCategoryId(user.getId(), category.getCode());
        });

        return UserDto.of(findById(userDto.getId()));
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
