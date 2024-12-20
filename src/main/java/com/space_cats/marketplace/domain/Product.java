package com.space_cats.marketplace.domain;

import com.space_cats.marketplace.common.ProductAvailability;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Builder
@Data
public class Product {
    private UUID id;
    private String title;
    private String description;
    private double price;
    private ProductAvailability availability;
    Category category;
}
