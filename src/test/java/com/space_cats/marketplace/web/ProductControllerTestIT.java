package com.space_cats.marketplace.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.space_cats.marketplace.common.ProductAvailability;
import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.domain.Product;
import com.space_cats.marketplace.DTO.product.ProductRequestDto;
import com.space_cats.marketplace.service.ProductService;
import com.space_cats.marketplace.service.exception.Category_404;
import com.space_cats.marketplace.service.exception.Product_404;
import com.space_cats.marketplace.service.mappers.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@AutoConfigureMockMvc
@SpringBootTest
public class ProductControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ProductMapper productMapper;

    private Product product;

    private ProductRequestDto productRequestDto;
    private static final String PRODUCT_ID = "a4f29e75-d264-439d-a228-4f8ccc8a3050";


    @BeforeEach
    public void init() {
        Category category = Category.builder().id(1).title("star 1").description("Cat1").build();
        product = Product.builder().id(UUID.fromString("a4f29e75-d264-439d-a228-4f8ccc8a3050")).title("Test_test").description("description").price(100.0).availability(ProductAvailability.AVAILABLE).category(category).build();
        productRequestDto = ProductRequestDto.builder().title("star 2").description("TEST_DESCRIPTION").availability(ProductAvailability.AVAILABLE).categoryId(1).price(100.0).build();
    }


    @Test
    public void shouldCreateProductSuccess() throws Exception {
        Mockito.when(productService.createProduct(productRequestDto)).thenReturn(product);

        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(productRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void shouldCreateProductFail() throws Exception {
        Mockito.when(productService.createProduct(productRequestDto)).thenReturn(product);

        ResultActions response = mockMvc.perform(post("/api/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void shouldGetAllProducts() throws Exception {
        List<Product> response = List.of(product);
        Mockito.when(productService.getAllProducts()).thenReturn(response);


        mockMvc.perform(get("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductResponseDtoList(response))));
    }

    @Test
    public void shouldGetProductById() throws Exception {
        Mockito.when(productService.getProductById(PRODUCT_ID)).thenReturn(product);

        mockMvc.perform(get("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductResponseDto(product))));

    }

    @Test
    public void shouldGetProductByIdFail() throws Exception {
        Mockito.when(productService.getProductById(PRODUCT_ID)).thenThrow(Product_404.class);

        mockMvc.perform(get("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldUpdateProductSuccess() throws Exception {
        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID)).thenReturn(product);

        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(productMapper.toProductResponseDto(product))));
    }

    @Test
    public void shouldUpdateProductNotFoundCategoryException() throws Exception {
        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID)).thenThrow(Category_404.class);

        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void shouldUpdateProductNotFoundProductException() throws Exception {
        Mockito.when(productService.updateProduct(productRequestDto, PRODUCT_ID)).thenThrow(Product_404.class);

        mockMvc.perform(put("/api/v1/products/{id}", PRODUCT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void shouldRejectInvalidProductPrice() throws Exception {
        ProductRequestDto invalidProduct = productRequestDto.toBuilder()
                .price(-100.0)
                .build();

        mockMvc.perform(post("/api/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidProduct)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }



}
