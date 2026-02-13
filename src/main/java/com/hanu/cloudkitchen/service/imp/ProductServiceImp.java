package com.hanu.cloudkitchen.service.imp;

import com.hanu.cloudkitchen.entity.Product;
import com.hanu.cloudkitchen.repository.CategoryRepo;
import com.hanu.cloudkitchen.repository.ProductRepo;
import com.hanu.cloudkitchen.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private ProductRepo productRepo;
    private CategoryRepo categoryRepo;

    public ProductServiceImp(ProductRepo productRepo, CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Product create(Product product) {
        return productRepo.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepo.findAll();
    }

    @Override
    public List<Product> findByCategory(Long id) {
        return productRepo.findByCategory(categoryRepo.findById(id).orElseThrow(() -> new RuntimeException("category not found")));
    }
}
