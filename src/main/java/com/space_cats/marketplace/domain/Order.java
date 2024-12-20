package com.space_cats.marketplace.domain;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.util.List;
import java.util.UUID;

@Value
@Data
@Builder
public class Order {
    UUID id;
    String Name;
    String address;
    String email;
    String orderStatus;
    List<OrderPosition> orderPositions;
    double total;
}
