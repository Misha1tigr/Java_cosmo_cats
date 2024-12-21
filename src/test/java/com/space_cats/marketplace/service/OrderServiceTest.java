package com.space_cats.marketplace.service;

import com.space_cats.marketplace.DTO.order.OrderItemDto;
import com.space_cats.marketplace.DTO.order.OrderRequestDto;
import com.space_cats.marketplace.DTO.order.OrderResponseDto;
import com.space_cats.marketplace.service.implementation.Order_Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Order Service Test")
@SpringBootTest(classes = {Order_Service.class})
public class OrderServiceTest {

    @Autowired
    @Spy
    private OrderService orderService;

    private OrderRequestDto orderRequestDto;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        List<OrderItemDto> orderItemDtoList = new ArrayList<>();
        orderItemDtoList.add(new OrderItemDto("Product 1", 30, 2.0));
        orderItemDtoList.add(new OrderItemDto("Product 2", 11, 1.0));

        orderRequestDto = OrderRequestDto.builder()
                .customerName("Frank G Lightfoot")
                .email("carole2015@yahoo.com")
                .address("2626 Worley Avenue")
                .orderItems(orderItemDtoList)
                .totalPrice(71.0)
                .orderStatus("COMPLETED")
                .build();
    }

    @Test
    public void testAddOrder() {
        OrderResponseDto order = orderService.addOrder(orderRequestDto);

        assertEquals(orderRequestDto.getCustomerName(), order.getConsumerName());
        assertEquals(orderRequestDto.getAddress(), order.getAddress());
        assertEquals(orderRequestDto.getEmail(), order.getEmail());
        assertEquals(orderRequestDto.getTotalPrice(), order.getTotalPrice());
        assertEquals(orderRequestDto.getOrderStatus(), order.getOrderStatus());
        assertEquals(orderRequestDto.getOrderItems().size(), order.getOrderItems().size());
    }

    @Test
    void shouldHandleEmptyOrderItems() {
        OrderRequestDto emptyOrderRequest = orderRequestDto.toBuilder()
                .orderItems(new ArrayList<>())
                .build();

        OrderResponseDto response = orderService.addOrder(emptyOrderRequest);
        assertTrue(response.getOrderItems().isEmpty());
    }

    @Test
    void shouldCalculateTotalPriceCorrectly() {
        OrderResponseDto response = orderService.addOrder(orderRequestDto);
        double expectedTotal = orderRequestDto.getOrderItems().stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();
        assertEquals(expectedTotal, response.getTotalPrice());
    }
}

