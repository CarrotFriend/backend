package com.carrotfriend.repository;

import com.carrotfriend.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    public Optional<Category> findCategoryByCodeAndName(Long code, String name);
    public Optional<Category> findCategoryByCode(Long code);
}
