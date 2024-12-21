package com.space_cats.marketplace.service.mappers;

import static org.junit.jupiter.api.Assertions.*;
import com.space_cats.marketplace.common.ProductAvailability;
import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.domain.Product;
import com.space_cats.marketplace.DTO.product.ProductResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class ProductMapperTest {
    private static final String PRODUCT_ID = "a4f29e75-d264-439d-a228-4f8ccc8a3050";

    private final ProductMapper productMapper = Mappers.getMapper(ProductMapper.class);

    private Product product;
    private Category category;
    List<Product> products;

    @BeforeEach
    void init() {
        category = Category.builder()
                .id(1L)
                .title("Test_TEST")
                .description("Description_DESCRIPTION")
                .build();

        product = Product.builder()
                .id(UUID.fromString(PRODUCT_ID))
                .title("Product")
                .description("Description of a product")
                .price(123)
                .availability(ProductAvailability.AVAILABLE)
                .category(category)
                .build();
        products = List.of(product);
    }

    @Test
    void shouldMapProductToProductResponseDto() {
        ProductResponseDto productResponseDto = productMapper.toProductResponseDto(product);

        assertNotNull(productResponseDto);
        assertEquals(product.getId().toString(), productResponseDto.getId());
        assertEquals(product.getTitle(), productResponseDto.getTitle());
        assertEquals(product.getDescription(), productResponseDto.getDescription());
        assertEquals(product.getPrice(), productResponseDto.getPrice());
        assertEquals(product.getCategory().getTitle(), productResponseDto.getCategory().getTitle());
        assertEquals(product.getCategory().getDescription(), productResponseDto.getCategory().getDescription());
        assertEquals(product.getAvailability().getUserFriendlyStatus(), productResponseDto.getAvailability());
    }

    @Test
    void shouldMapProductListToProductResponseDtoList() {
        List<ProductResponseDto> productResponseDtoList = productMapper.toProductResponseDtoList(products);

        assertNotNull(productResponseDtoList);
        assertEquals(productResponseDtoList.size(), products.size());
    }
}
