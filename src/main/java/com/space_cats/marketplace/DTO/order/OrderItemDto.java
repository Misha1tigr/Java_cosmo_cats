package com.space_cats.marketplace.DTO.order;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Value;

@Value
public class OrderItemDto {
    @NotNull(message = "Product name cannot be empty")
    String product;

    @NotNull(message = "Product quantity must be chosen")
    @Positive(message = "Product quantity must be greater than zero")
    int quantity;

    @NotNull(message = "Product price must be set")
    @Positive(message = "Product price must be greater than zero")
    double price;
}
