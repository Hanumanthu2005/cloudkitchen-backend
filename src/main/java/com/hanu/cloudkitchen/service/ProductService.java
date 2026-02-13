package com.hanu.cloudkitchen.service;

import com.hanu.cloudkitchen.entity.Product;

import java.util.List;

public interface ProductService {
    Product create(Product product);

    List<Product> getAll();

    List<Product> findByCategory(Long id);
}
