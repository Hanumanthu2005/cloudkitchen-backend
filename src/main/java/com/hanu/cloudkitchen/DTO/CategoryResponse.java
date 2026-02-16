package com.hanu.cloudkitchen.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CategoryResponse {

    private Long id;
    private String name;
    private String description;
    private List<Long> productIds;

    public CategoryResponse(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.productIds = List.of(); // avoid null
    }
}
