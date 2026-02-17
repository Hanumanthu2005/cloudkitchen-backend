package com.hanu.cloudkitchen.controller;

import com.hanu.cloudkitchen.DTO.request.CategoryRequest;
import com.hanu.cloudkitchen.DTO.response.CategoryResponse;
import com.hanu.cloudkitchen.DTO.response.ProductResponse;
import com.hanu.cloudkitchen.service.CategoryService;
import com.hanu.cloudkitchen.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService,
                              ProductService productService) {
        this.categoryService = categoryService;
        this.productService = productService;
    }

    // -------- CREATE CATEGORY --------

    @PostMapping
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.create(request));
    }

    // -------- GET PRODUCTS BY CATEGORY --------

    @GetMapping("/{id}/products")
    public ResponseEntity<List<ProductResponse>> findProductsByCategory(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                productService.findByCategory(id)
        );
    }

    // -------- GET ALL CATEGORIES --------

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAll() {
        return ResponseEntity.ok(
                categoryService.findAll()
        );
    }
}
