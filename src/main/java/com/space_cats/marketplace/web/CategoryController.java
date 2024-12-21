package com.space_cats.marketplace.web;

import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.DTO.category.CategoryDto;
import com.space_cats.marketplace.service.CategoryService;
import com.space_cats.marketplace.service.mappers.CategoryMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/categories")
@Validated
public class CategoryController {
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;

    public CategoryController(CategoryService categoryService, CategoryMapper categoryMapper) {
        this.categoryService = categoryService;
        this.categoryMapper = categoryMapper;
    }

    @GetMapping
    public ResponseEntity<List<CategoryDto>> getAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        return ResponseEntity.ok(categoryMapper.categoryListToCategoryDtoList(categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategoryById(@PathVariable int id) {
        Category category = categoryService.findCategoryById(id);
        return ResponseEntity.ok(categoryMapper.categoryToCategoryDto(category));
    }
}
