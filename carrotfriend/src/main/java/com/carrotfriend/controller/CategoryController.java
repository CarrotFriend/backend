package com.carrotfriend.controller;

import com.carrotfriend.dto.user.CategoryDto;
import com.carrotfriend.service.CategoryService;
import com.carrotfriend.util.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;
    private final Response response;

    @GetMapping("/{code}")
    public ResponseEntity<?> findOneByCode(@PathVariable Long code){
        return response.success(CategoryDto.of(categoryService.findOneByCode(code)));
    }
    @GetMapping("")
    public ResponseEntity<?> findAll(){
        return response.success(categoryService.findAll());
    }
    @PostMapping("")
    public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto){
        return response.success(categoryService.createCategory(categoryDto));
    }
}
