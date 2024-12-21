package com.space_cats.marketplace.service;

import com.space_cats.marketplace.common.ProductAvailability;
import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.domain.Product;
import com.space_cats.marketplace.DTO.product.ProductRequestDto;
import com.space_cats.marketplace.service.exception.Category_404;
import com.space_cats.marketplace.service.exception.Product_404;
import com.space_cats.marketplace.service.implementation.Product_Service;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Product Service Test")
@SpringBootTest(classes = {Product_Service.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ProductServiceTest {
    private static final int CATEGORY_ID = 1;
    private static final String CATEGORY_TITLE = "test_category";
    private static final String CATEGORY_DESCRIPTION = "test_description";
    private static final String PRODUCT_ID = "a4f29e75-d264-439d-a228-4f8ccc8a3050";

    @MockBean
    private CategoryService mockCategoryService;

    @Autowired
    @Spy
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @Order(1)
    void shouldGetProductList() {
        List<Product> productList = productService.getAllProducts();
        assertNotNull(productList);
    }

    @Test
    @Order(2)
    void shouldGetProductById() {
        Category category = categoryBuilder();

        Product mockProduct = productBuilder(category);

        Mockito.when(productService.getProductById(PRODUCT_ID)).thenReturn(mockProduct);
        assertEquals(mockProduct, productService.getProductById(PRODUCT_ID));
    }

    @Test
    @Order(3)
    void shouldGetNotFoundProduct() {
        Mockito.when(productService.getProductById(PRODUCT_ID))
                .thenThrow(new Product_404(PRODUCT_ID));

        assertThrows(Product_404.class, () -> productService.getProductById(PRODUCT_ID));
    }

    @Test
    @Order(4)
    void shouldCreateProductSuccess() {
        Category category = categoryBuilder();

        ProductRequestDto productRequestDto = productRequestDtoBuilder();

        Mockito.when(mockCategoryService.findCategoryById(CATEGORY_ID)).thenReturn(category);
        Product product = productService.createProduct(productRequestDto);

        assertNotNull(product.getId());
        assertEquals("Test_title_2", product.getTitle());
        assertEquals("Test_description_2", product.getDescription());
        assertEquals(33.0, product.getPrice());
        assertEquals(category, product.getCategory());
        assertEquals(ProductAvailability.AVAILABLE, product.getAvailability());
    }

    @Test
    @Order(5)
    void shouldThrowExceptionCreateProductFailed() {
        ProductRequestDto productRequestDto = productRequestDtoBuilder();

        Mockito.when(productService.createProduct(productRequestDto)).thenThrow(new Category_404(CATEGORY_ID));
        assertThrows(Category_404.class, () -> productService.createProduct(productRequestDto));
    }

    @Test
    @Order(6)
    void shouldUpdateProductSuccess() {
        Category category = categoryBuilder();

        ProductRequestDto productRequestDto = productRequestDtoBuilder();

        Mockito.when(mockCategoryService.findCategoryById(CATEGORY_ID)).thenReturn(category);
        Product product = productService.updateProduct(productRequestDto, PRODUCT_ID);

        assertNotNull(product.getId());
        assertEquals("Test_title_2", product.getTitle());
        assertEquals("Test_description_2", product.getDescription());
        assertEquals(33.0, product.getPrice());
        assertEquals(category, product.getCategory());
        assertEquals(ProductAvailability.AVAILABLE, product.getAvailability());
    }

    @Test
    @Order(7)
    void shouldThrowExceptionUpdateProductFailedProductNotFound() {
        ProductRequestDto productRequestDto = productRequestDtoBuilder();

        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID))
                .thenThrow(new Product_404(PRODUCT_ID));

        assertThrows(Product_404.class, () -> productService.updateProduct(productRequestDto, PRODUCT_ID));
    }

    @Test
    @Order(8)
    void shouldThrowExceptionUpdateProductFailedCategoryNotFound() {
        ProductRequestDto productRequestDto = productRequestDtoBuilder();

        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID))
                .thenThrow(new Category_404(CATEGORY_ID));
        assertThrows(Category_404.class, () -> productService.updateProduct(productRequestDto, PRODUCT_ID));
    }


    @Test
    @Order(9)
    void shouldThrowExceptionDeleteProductFailedProductNotFound() {
        Mockito.when(productService.deleteProduct("d90e16fd-b349-4c14-9b2b-8cbb2d70ff64"))
                .thenThrow(new Product_404("d90e16fd-b349-4c14-9b2b-8cbb2d70ff64"));

        assertThrows(Product_404.class, () -> productService.deleteProduct("d90e16fd-b349-4c14-9b2b-8cbb2d70ff64"));
    }


    private static Category categoryBuilder() {
        return Category.builder()
                .id(CATEGORY_ID)
                .title(CATEGORY_TITLE)
                .description(CATEGORY_DESCRIPTION)
                .build();
    }

    private static Product productBuilder(Category category) {
        return Product.builder()
                .id(UUID.fromString(PRODUCT_ID))
                .title("Test_title")
                .category(category)
                .availability(ProductAvailability.AVAILABLE)
                .description("Test_description")
                .price(100)
                .build();
    }

    private static ProductRequestDto productRequestDtoBuilder() {
        return ProductRequestDto.builder()
                .title("Test_title_2")
                .categoryId(CATEGORY_ID)
                .price(33.0)
                .description("Test_description_2")
                .availability(ProductAvailability.AVAILABLE)
                .build();
    }
}
