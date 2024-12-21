package com.space_cats.marketplace.DTO.product;

import com.space_cats.marketplace.DTO.category.CategoryDto;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class ProductResponseDto {
    String id;
    String title;
    String description;
    Double price;
    String availability;
    CategoryDto category;
}
