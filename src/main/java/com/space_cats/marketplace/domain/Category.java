package com.space_cats.marketplace.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Category {
    private long id;
    private String title;
    private String description;
}
