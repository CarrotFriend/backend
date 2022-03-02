package com.carrotfriend.repository;

import com.carrotfriend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findByUserId(String userId);
    public User findByEmail(String email);
}
