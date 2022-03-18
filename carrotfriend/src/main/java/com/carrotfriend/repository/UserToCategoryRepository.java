package com.carrotfriend.repository;

import com.carrotfriend.domain.UserToCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserToCategoryRepository extends JpaRepository<UserToCategory, Long> {
}
