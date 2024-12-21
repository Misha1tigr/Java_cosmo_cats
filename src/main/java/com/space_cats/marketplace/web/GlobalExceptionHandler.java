package com.space_cats.marketplace.web;

import com.space_cats.marketplace.service.exception.Category_404;
import com.space_cats.marketplace.service.exception.Product_404;
import com.space_cats.marketplace.web.exception.FailedFields;
import org.springframework.http.*;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.net.URI;
import java.util.List;

import static com.space_cats.marketplace.error.GenerateErrorDetalis.getValidationErrors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Product_404.class)
    public ProblemDetail handleProductNotFoundException(Product_404 ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("product-not-found"));
        problemDetail.setTitle("Product does not exist");
        return problemDetail;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(Category_404.class)
    public ProblemDetail handleCategoryNotFoundException(Category_404 ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setType(URI.create("category-not-found"));
        problemDetail.setTitle("Category does not exist");
        return problemDetail;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
        List<FailedFields> validationResponse = fieldErrors.stream().map(error -> FailedFields.builder()
                        .fieldName(error.getField())
                        .reason(error.getDefaultMessage())
                        .build())
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(getValidationErrors(validationResponse));
    }
}
