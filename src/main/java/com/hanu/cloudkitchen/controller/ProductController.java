package com.hanu.cloudkitchen.controller;

import com.hanu.cloudkitchen.entity.Category;
import com.hanu.cloudkitchen.entity.Product;
import com.hanu.cloudkitchen.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("products")
public class ProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody Product product) {
        return ResponseEntity.ok(productService.create(product));
    }


}
