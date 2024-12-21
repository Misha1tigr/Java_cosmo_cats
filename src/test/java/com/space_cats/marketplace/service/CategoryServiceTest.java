package com.space_cats.marketplace.service;

import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.service.exception.Category_404;
import com.space_cats.marketplace.service.implementation.Category_Service;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Category Service Test")
@SpringBootTest(classes = {Category_Service.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CategoryServiceTest {
    private static final int CATEGORY_ID = 1;
    private static final String CATEGORY_TITLE = "Test_Title";
    private static final String CATEGORY_DESCRIPTION = "Test_Description";

    @Autowired
    @Spy
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void shouldFindAllCategories() {
        List<Category> categoryList = categoryService.findAllCategories();
        assertNotNull(categoryList);
    }

    @Test
    @Order(2)
    void shouldFindCategoryById() {
        Category mockCategory = categoryBuilder();

        Mockito.when(categoryService.findCategoryById(CATEGORY_ID)).thenReturn(mockCategory);
        assertEquals(mockCategory, categoryService.findCategoryById(CATEGORY_ID));
    }

    @Test
    @Order(3)
    void shouldGetNotFoundCategory() {
        Mockito.when(categoryService.findCategoryById(CATEGORY_ID))
                .thenThrow(new Category_404(CATEGORY_ID));

        assertThrows(Category_404.class, () -> categoryService.findCategoryById(CATEGORY_ID));
    }

    private static Category categoryBuilder() {
        return Category.builder()
                .id(CATEGORY_ID)
                .title(CATEGORY_TITLE)
                .description(CATEGORY_DESCRIPTION)
                .build();
    }
}
