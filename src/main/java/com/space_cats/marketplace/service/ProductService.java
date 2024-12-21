package com.space_cats.marketplace.service;

import com.space_cats.marketplace.domain.Product;
import com.space_cats.marketplace.DTO.product.ProductRequestDto;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getProductById(String productId);
    Product createProduct(ProductRequestDto productRequestDto);
    Product updateProduct(ProductRequestDto productRequestDto, String id);
    String deleteProduct(String id);
}
