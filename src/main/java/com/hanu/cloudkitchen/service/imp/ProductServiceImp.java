package com.hanu.cloudkitchen.service.imp;

import com.hanu.cloudkitchen.DTO.ProductRequest;
import com.hanu.cloudkitchen.DTO.ProductResponse;
import com.hanu.cloudkitchen.entity.Category;
import com.hanu.cloudkitchen.entity.Product;
import com.hanu.cloudkitchen.exception.ResourceNotFoundException;
import com.hanu.cloudkitchen.repository.CategoryRepo;
import com.hanu.cloudkitchen.repository.ProductRepo;
import com.hanu.cloudkitchen.service.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImp implements ProductService {

    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;

    public ProductServiceImp(ProductRepo productRepo,
                             CategoryRepo categoryRepo) {
        this.productRepo = productRepo;
        this.categoryRepo = categoryRepo;
    }

    // ---------- CREATE ----------

    @Override
    public ProductResponse create(ProductRequest request) {

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setImageUrl(request.getImageUrl());
        product.setCategory(resolveCategory(request));

        Product saved = productRepo.save(product);

        return mapToResponse(saved);
    }

    // ---------- READ ALL ----------

    @Override
    public List<ProductResponse> getAll() {

        return productRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ---------- READ BY ID ----------

    @Override
    public ProductResponse getById(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        return mapToResponse(product);
    }

    // ---------- FIND BY CATEGORY ----------

    @Override
    public List<ProductResponse> findByCategory(Long id) {

        Category category = categoryRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found"));

        return productRepo.findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // ---------- UPDATE ----------

    @Override
    @Transactional
    public ProductResponse updateProduct(Long id, ProductRequest request) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Invalid product id"));

        if (request.getName() != null)
            product.setName(request.getName());

        if (request.getDescription() != null)
            product.setDescription(request.getDescription());

        if (request.getPrice() != null)
            product.setPrice(request.getPrice());

        if (request.getImageUrl() != null)
            product.setImageUrl(request.getImageUrl());

        if (request.getCategoryId() != null ||
                (request.getCategoryName() != null &&
                        !request.getCategoryName().isBlank())) {
            product.setCategory(resolveCategory(request));
        }

        return mapToResponse(product);
    }

    // ---------- DELETE ----------

    @Override
    public void deleteProduct(Long id) {

        Product product = productRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        productRepo.delete(product);
    }

    // ---------- HELPER ----------

    private Category resolveCategory(ProductRequest request) {

        if (request.getCategoryId() != null &&
                request.getCategoryName() != null &&
                !request.getCategoryName().isBlank()) {

            throw new IllegalArgumentException(
                    "Provide either category id OR new category name"
            );
        }

        if (request.getCategoryId() != null) {
            return categoryRepo.findById(request.getCategoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Category not found"));
        }

        if (request.getCategoryName() != null &&
                !request.getCategoryName().isBlank()) {

            Category category = new Category();
            category.setName(request.getCategoryName());
            category.setDescription(request.getCategoryDescription());

            return categoryRepo.save(category);
        }

        throw new IllegalArgumentException("Category information is required");
    }

    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getImageUrl(),
                product.getPrice(),
                product.getCategory() != null
                        ? product.getCategory().getId()
                        : null
        );
    }
}
