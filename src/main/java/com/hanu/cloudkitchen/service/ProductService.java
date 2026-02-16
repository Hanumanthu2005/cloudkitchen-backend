package com.hanu.cloudkitchen.service;

import com.hanu.cloudkitchen.DTO.ProductRequest;
import com.hanu.cloudkitchen.DTO.ProductResponse;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface ProductService {
    ProductResponse create(ProductRequest request);

    List<ProductResponse> getAll();

    List<ProductResponse> findByCategory(Long id);

    ProductResponse updateProduct(Long id, ProductRequest request);

    void deleteProduct(Long id);

    @Nullable ProductResponse getById(Long id);
}
