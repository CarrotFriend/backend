package com.carrotfriend.service;

import com.carrotfriend.domain.User;
import com.carrotfriend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service("userDetailService")
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUserId(username).map(user->toUserDetails(username, user)).orElseThrow(()->new UsernameNotFoundException(username+" has not found!"));
    }
    private UserDetails toUserDetails(String username, User user){
        // 나중에 수정
        List<GrantedAuthority> auth = new ArrayList<>();
        auth.add(new SimpleGrantedAuthority("USER_ROLE"));
        return new org.springframework.security.core.userdetails.User(user.getUserId(), user.getPw(), auth);
    }
}

