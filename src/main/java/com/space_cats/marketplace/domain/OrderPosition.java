package com.space_cats.marketplace.domain;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class OrderPosition {
    String id;
    int number;
    double price;
}
