package com.carrotfriend.service;

import com.carrotfriend.domain.Category;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Transactional
    public CategoryDto createCategory(CategoryDto categoryDto){
        return CategoryDto.of(categoryRepository.save(Category.builder()
                .code(categoryDto.getCode())
                .name(categoryDto.getName()).build()));
    }

    public Category findOneByCode(Long code){
        Category category = categoryRepository.findCategoryByCode(code).orElseGet(null);
        return category;
    }
    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(c->CategoryDto.of(c)).collect(Collectors.toList());
    }
    public Category findCategoryByCodeAndName(Long code, String name){
        Category category = categoryRepository.findCategoryByCodeAndName(code,name).orElseGet(null);
        return category;
    }
}
