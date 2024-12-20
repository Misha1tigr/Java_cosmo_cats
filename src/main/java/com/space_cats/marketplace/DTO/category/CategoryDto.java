package com.space_cats.marketplace.DTO.category;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Value;

@Value
@Builder(toBuilder = true)
public class CategoryDto {
    @NotBlank(message = "Title cannot be blank")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    String title;

    @NotBlank(message = "Description cannot be blank")
    @Size(max = 255, message = "Description cannot be longer then 255 characters")
    String description;
}
