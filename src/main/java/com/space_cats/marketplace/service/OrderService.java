package com.space_cats.marketplace.service;

import com.space_cats.marketplace.DTO.order.OrderRequestDto;
import com.space_cats.marketplace.DTO.order.OrderResponseDto;

public interface OrderService {
    OrderResponseDto addOrder(OrderRequestDto orderRequestDto);
}
