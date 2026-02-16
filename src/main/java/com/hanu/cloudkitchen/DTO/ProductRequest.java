package com.hanu.cloudkitchen.DTO;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductRequest {
    private String name;

    private String description;

    private String imageUrl;

    private BigDecimal price;

    private Long categoryId;

    private String categoryName;

    private String categoryDescription;
}
