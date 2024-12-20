package com.space_cats.marketplace.service.implementation;

import com.space_cats.marketplace.common.ProductAvailability;
import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.domain.Product;
import com.space_cats.marketplace.DTO.product.ProductRequestDto;
import com.space_cats.marketplace.service.CategoryService;
import com.space_cats.marketplace.service.ProductService;
import com.space_cats.marketplace.service.exception.Product_404;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class Product_Service implements ProductService {

    private final CategoryService categoryService;

    private final List<Product> productList = new ArrayList<>();

    public Product_Service(CategoryService categoryService) {
        this.categoryService = categoryService;

        productList.add(Product.builder()
                .id(UUID.fromString("a4f29e75-d264-439d-a228-4f8ccc8a3050"))
                .title("Starlight Telescope")
                .description("A high-precision telescope for observing distant galaxies.")
                .price(799.99)
                .availability(ProductAvailability.AVAILABLE)
                .category(categoryService.findCategoryById(1))
                .build());
        productList.add(Product.builder()
                .id(UUID.fromString("d90e16fd-b349-4c14-9b2b-8cbb2d70ff64"))
                .title("Meteorite Fragment")
                .description("A genuine piece of a meteorite that fell to Earth.")
                .price(143)
                .availability(ProductAvailability.UNAVAILABLE)
                .category(categoryService.findCategoryById(2))
                .build());
        productList.add(Product.builder()
                .id(UUID.randomUUID())
                .title("Cosmic Ray Detector")
                .description("Detects cosmic rays from deep space, ideal for amateur astronomers.")
                .price(184.77)
                .availability(ProductAvailability.BACK_SOON)
                .category(categoryService.findCategoryById(1))                .
                build());
    }

    @Override
    public List<Product> getAllProducts() {
        return productList;
    }

    @Override
    public Product getProductById(String productId) {
        return productList.stream().filter(item -> item.getId().toString()
                        .equals(productId))
                .findFirst()
                .orElseThrow(() -> new Product_404(productId));
    }

    @Override
    public Product createProduct(ProductRequestDto productRequestDto) {
        Category category = categoryService.findCategoryById(productRequestDto.getCategoryId());

        Product product = Product.builder()
                .id(UUID.randomUUID())
                .title(productRequestDto.getTitle())
                .description(productRequestDto.getDescription())
                .price(productRequestDto.getPrice())
                .availability(productRequestDto.getAvailability())
                .category(category)
                .build();
        productList.add(product);
        return product;
    }

    @Override
    public Product updateProduct(ProductRequestDto productRequestDto, String id) {
        Product product = getProductById(id);
        Category category = categoryService.findCategoryById(productRequestDto.getCategoryId());

        product.setTitle(productRequestDto.getTitle());
        product.setDescription(productRequestDto.getDescription());
        product.setPrice(productRequestDto.getPrice());
        product.setAvailability(productRequestDto.getAvailability());
        product.setCategory(category);

        return product;
    }

    @Override
    public String deleteProduct(String id) {
        Product product = getProductById(id);
        productList.remove(product);
        return "Product with ID - " + id + " has been deleted";
    }
}
