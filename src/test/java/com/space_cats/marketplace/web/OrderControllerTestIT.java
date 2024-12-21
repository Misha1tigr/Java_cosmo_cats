package com.space_cats.marketplace.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.space_cats.marketplace.domain.Category;
import com.space_cats.marketplace.domain.Order;
import com.space_cats.marketplace.DTO.order.OrderItemDto;
import com.space_cats.marketplace.DTO.order.OrderRequestDto;
import com.space_cats.marketplace.DTO.order.OrderResponseDto;
import com.space_cats.marketplace.service.CategoryService;
import com.space_cats.marketplace.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.matchers.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@AutoConfigureMockMvc
@SpringBootTest
public class OrderControllerTestIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    OrderRequestDto orderRequestDto;

    OrderResponseDto orderResponseDto;

    @BeforeEach
    public void init() {
        List<OrderItemDto> orderItems = new ArrayList<>(List.of(
                new OrderItemDto("Product 123", 1, 23.0),
                new OrderItemDto("Product 123", 10, 1.77)
        ));

        orderRequestDto = OrderRequestDto.builder()
                .customerName("Norman N Velasco")
                .email("emily_wyma8@hotmail.com")
                .orderStatus("ON_HOLD")
                .totalPrice(100)
                .orderItems(orderItems)
                .address("648 Farnum Road")
                .build();

        orderResponseDto = OrderResponseDto.builder()
                .id(UUID.fromString("a4f29e75-d264-439d-a228-4f8ccc8a3050"))
                .address(orderRequestDto.getAddress())
                .consumerName(orderRequestDto.getCustomerName())
                .email(orderRequestDto.getEmail())
                .orderItems(orderRequestDto.getOrderItems())
                .totalPrice(orderRequestDto.getTotalPrice())
                .build();
    }

    @Test
    public void shouldPlaceOrder() throws Exception {
        Mockito.when(orderService.addOrder(orderRequestDto)).thenReturn(orderResponseDto);

        ResultActions response = mockMvc.perform(post("/api/v1/orders/add-order")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequestDto)));

        response.andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldRejectOrderWithInvalidEmail() throws Exception {
        OrderRequestDto invalidOrder = orderRequestDto.toBuilder()
                .email("invalid-email")
                .build();

        mockMvc.perform(post("/api/v1/orders/add-order")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidOrder)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void shouldHandleEmptyRequestBody() throws Exception {
        mockMvc.perform(post("/api/v1/orders/add-order")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
