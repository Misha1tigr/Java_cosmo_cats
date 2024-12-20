package com.space_cats.marketplace.DTO.order;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder(toBuilder = true)
public class OrderRequestDto {
    @NotNull(message = "Order items cannot be empty")
    List<OrderItemDto> orderItems;

    @NotBlank(message = "Address cannot be blank")
    @Size(max = 255, message = "Address cannot exceed 255 characters")
    String address;

    @Email(message = "Email should exist")
    String email;

    @NotBlank(message = "Customer name cannot be blank")
    @Size(max = 255, message = "Customer name cannot exceed 255 characters")
    String customerName;

    @NotNull(message = "Total price cannot be null")
    @Positive(message = "Total price must be greater than zero")
    double totalPrice;

    @NotBlank(message = "Status cannot be null or blank")
    String orderStatus;
}
