package com.space_cats.marketplace.DTO.product;

import com.space_cats.marketplace.common.ProductAvailability;
import com.space_cats.marketplace.DTO.validation.Extended;
import com.space_cats.marketplace.DTO.validation.ValidNames;
import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@GroupSequence({ProductRequestDto.class, Extended.class})
@Builder(toBuilder = true)
public class ProductRequestDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot be longer then 100 characters")
    @ValidNames(groups = Extended.class)
    String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot be longer then 255 characters")
    String description;

    @NotNull(message = "Price has to be set")
    Double price;

    @NotNull(message = "Category has to be set")
    Integer categoryId;

    ProductAvailability availability;
}
