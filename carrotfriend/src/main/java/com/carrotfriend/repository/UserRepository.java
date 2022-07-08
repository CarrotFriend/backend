package com.carrotfriend.repository;

import com.carrotfriend.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByUserId(String userId);
    public Optional<User> findByEmail(String email);
}
