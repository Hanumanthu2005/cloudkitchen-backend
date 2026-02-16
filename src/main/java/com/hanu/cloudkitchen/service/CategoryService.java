package com.hanu.cloudkitchen.service;

import com.hanu.cloudkitchen.DTO.CategoryRequest;
import com.hanu.cloudkitchen.DTO.CategoryResponse;
import com.hanu.cloudkitchen.entity.Category;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> findAll();
}
