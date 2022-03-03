package com.carrotfriend.service;

import com.carrotfriend.domain.User;
import com.carrotfriend.domain.UserToCategory;
import com.carrotfriend.dto.UserRequestDto;
import com.carrotfriend.dto.UserResponseDto;
import com.carrotfriend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto.User join(UserRequestDto.JoinUser joinUser){
        User user = userRepository.save(com.carrotfriend.domain.User.builder()
                .userID(joinUser.getUserID())
                .pw(passwordEncoder.encode(joinUser.getPw()))
                .nickName(joinUser.getNickName())
                .email(joinUser.getEmail())
                .gender(joinUser.getGender())
                .birthday(joinUser.getBirthday())
                .build());

        return UserResponseDto.User.builder()
                .id(user.getId())
                .userID(user.getUserID())
                .nickName(user.getNickName())
                .build();
    }

    public UserResponseDto.User findById(Long id){
        User user = userRepository.findById(id).orElseGet(null);

        if(user == null) throw new UsernameNotFoundException("User Id : "+id+" not exist");

        UserResponseDto.User userdto = UserResponseDto.User.builder()
                .id(user.getId())
                .userID(user.getUserID())
                .nickName(user.getNickName())
                .build();
        for (UserToCategory userToCategory : user.getUserToCategoryList()) {
            userdto.setCategory(userToCategory.getCategory());
        }
        return userdto;
    }

}
