package com.hanu.cloudkitchen.controller;

import com.hanu.cloudkitchen.entity.Category;
import com.hanu.cloudkitchen.entity.Product;
import com.hanu.cloudkitchen.service.CategoryService;
import com.hanu.cloudkitchen.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService, ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createCategory(@RequestBody Category category) {
        return ResponseEntity.ok(categoryService.create(category));
    }

    @GetMapping("/{id}")
    public ResponseEntity<List<Product>> findByCategory(@PathVariable Long id) {
        return ResponseEntity.ok((productService.findByCategory(id)));
    }
}
