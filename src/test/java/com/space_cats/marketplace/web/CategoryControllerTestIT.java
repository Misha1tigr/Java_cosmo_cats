package com.space_cats.marketplace.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.service.CategoryService;
import com.space_cats.marketplace.service.exception.Category_404;
import com.space_cats.marketplace.service.mappers.CategoryMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@SpringBootTest
public class CategoryControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    private Category category;

    private static final int CATEGORY_ID = 1;

    @BeforeEach
    public void init() {
        category = Category.builder().id(1).title("Category 123").description("Category 123").build();
    }

    @Test
    public void shouldGetAllCategories() throws Exception {
        List<Category> response = List.of(category);
        Mockito.when(categoryService.findAllCategories()).thenReturn(response);

        mockMvc.perform(get("/api/v1/categories")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.categoryListToCategoryDtoList(response))));
    }

    @Test
    public void shouldGetCategoryById() throws Exception {
        Mockito.when(categoryService.findCategoryById(CATEGORY_ID)).thenReturn(category);

        mockMvc.perform(get("/api/v1/categories/{id}", CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(categoryMapper.categoryToCategoryDto(category))));
    }

    @Test
    public void shouldGetNotFoundException() throws Exception {
        Mockito.when(categoryService.findCategoryById(CATEGORY_ID)).thenThrow(Category_404.class);

        mockMvc.perform(get("/api/v1/categories/{id}", CATEGORY_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }
}

