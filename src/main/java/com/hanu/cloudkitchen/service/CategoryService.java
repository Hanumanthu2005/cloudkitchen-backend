package com.hanu.cloudkitchen.service;

import com.hanu.cloudkitchen.DTO.request.CategoryRequest;
import com.hanu.cloudkitchen.DTO.response.CategoryResponse;

import java.util.List;

public interface CategoryService {

    CategoryResponse create(CategoryRequest request);

    List<CategoryResponse> findAll();
}
