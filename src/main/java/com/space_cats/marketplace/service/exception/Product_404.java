package com.space_cats.marketplace.service.exception;

public class Product_404 extends RuntimeException {
    private static final String message = "Product with ID=%s does not exist";

    public Product_404(String id) {
        super(String.format(message, id));
    }
}
