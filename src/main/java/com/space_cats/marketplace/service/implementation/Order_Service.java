package com.space_cats.marketplace.service.implementation;

import com.space_cats.marketplace.DTO.order.OrderItemDto;
import com.space_cats.marketplace.DTO.order.OrderRequestDto;
import com.space_cats.marketplace.DTO.order.OrderResponseDto;
import com.space_cats.marketplace.service.OrderService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class Order_Service implements OrderService {

    @Override
    public OrderResponseDto addOrder(OrderRequestDto orderRequestDto) {
        return createOrderMock(
                orderRequestDto.getCustomerName(),
                orderRequestDto.getAddress(),
                orderRequestDto.getEmail(),
                orderRequestDto.getOrderStatus(),
                orderRequestDto.getOrderItems(),
                orderRequestDto.getTotalPrice()
        );
    }

    private OrderResponseDto createOrderMock(String consumerName, String address, String email, String orderStatus, List<OrderItemDto> orderItems, double totalPrice) {
        return OrderResponseDto.builder()
                .id(UUID.randomUUID())
                .consumerName(consumerName)
                .address(address)
                .email(email)
                .orderStatus(orderStatus)
                .orderItems(orderItems)
                .totalPrice(totalPrice)
                .build();
    }
}
