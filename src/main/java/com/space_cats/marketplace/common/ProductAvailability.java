package com.space_cats.marketplace.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum ProductAvailability {
    AVAILABLE("Buy now!"),
    BACK_SOON("Out of stock, will be back soon!"),
    UNKNOWN("Unknown"),
    UNAVAILABLE("Discontinued");
    private final String UserFriendlyStatus;
}
