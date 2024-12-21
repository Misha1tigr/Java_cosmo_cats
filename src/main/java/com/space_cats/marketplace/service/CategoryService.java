package com.space_cats.marketplace.service;

import com.space_cats.marketplace.domain.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAllCategories();
    Category findCategoryById(int category_id);
}
