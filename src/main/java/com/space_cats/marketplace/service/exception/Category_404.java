package com.space_cats.marketplace.service.exception;

public class Category_404 extends RuntimeException {
  private static final String message = "Category with ID=%s does not exist";

  public Category_404(int id) {
    super(String.format(message, id));
  }
}
