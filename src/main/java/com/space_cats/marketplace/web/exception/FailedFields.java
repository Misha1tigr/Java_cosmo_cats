package com.space_cats.marketplace.web.exception;

import lombok.Builder;
import lombok.Value;

@Builder
@Value
public class FailedFields {
    String fieldName;
    String reason;
}
