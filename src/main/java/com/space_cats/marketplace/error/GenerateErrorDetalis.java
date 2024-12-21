package com.space_cats.marketplace.error;

import com.space_cats.marketplace.web.exception.FailedFields;
import lombok.experimental.UtilityClass;
import org.springframework.http.ProblemDetail;

import java.net.URI;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

@UtilityClass
public class GenerateErrorDetalis {

    private static final String ERROR_TYPE_URI = "field-validation-error";
    private static final String TITLE_VALIDATION_EXCEPTION = "Field Validation Exception";

    public static ProblemDetail getValidationErrors(List<FailedFields> validationErrors) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(BAD_REQUEST, "Request validation failed");

        problemDetail.setTitle(TITLE_VALIDATION_EXCEPTION);
        problemDetail.setType(URI.create(ERROR_TYPE_URI));
        problemDetail.setProperty("invalidParams", mapValidationErrors(validationErrors));

        return problemDetail;
    }

    private static List<FailedFields> mapValidationErrors(List<FailedFields> validationErrors) {
        return validationErrors == null ? List.of() : validationErrors.stream()
                .filter(error -> error.getFieldName() != null && error.getReason() != null)
                .collect(toList());
    }
}
