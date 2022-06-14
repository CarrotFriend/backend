package com.carrotfriend.service;

import com.carrotfriend.domain.Category;
import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CategoryService {
    @PersistenceContext
    private final EntityManager em;
    private final CategoryRepository categoryRepository;
    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);
    @Transactional
    public Category createCategory(CategoryDto categoryDto){
        return categoryRepository.save(Category.builder()
                .name(categoryDto.getName()).build());
    }

    public List<Category> getCategoriesById(List<Long> ids){
        logger.info("query clause in = {}",ids);
        return em.createQuery("select c from Category c where c.id in (:ids)", Category.class)
                .setParameter("ids", ids)
                .getResultList();
    }
    public Category getOneById(Long code){
        Category category = categoryRepository.findCategoryById(code).orElseGet(null);
        return category;
    }
    public List<CategoryDto> findAll(){
        return categoryRepository.findAll().stream().map(c->CategoryDto.of(c)).collect(Collectors.toList());
    }
}
