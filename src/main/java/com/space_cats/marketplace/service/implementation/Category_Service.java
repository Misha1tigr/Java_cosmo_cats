package com.space_cats.marketplace.service.implementation;

import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.service.CategoryService;
import com.space_cats.marketplace.service.exception.Category_404;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class Category_Service implements CategoryService {
    List<Category> categoryList = new ArrayList<>(List.of(
            Category.builder().id(1L).title("Galactic Wonders").description("Exploring the universe's greatest marvels").build(),
            Category.builder().id(2L).title("Interstellar Adventures").description("Journeys beyond the stars").build()
    ));

    @Override
    public List<Category> findAllCategories() {
        return categoryList;
    }

    @Override
    public Category findCategoryById(int id) {
        return categoryList.stream().filter(category -> category.getId() == id).findFirst().orElseThrow(() -> new Category_404(id));
    }

    public void addCategory(Category category) {
        categoryList.add(category);
    }

    public void removeCategoryById(int categoryId) {
        categoryList.removeIf(cat -> cat.getId() == categoryId);
    }
}
