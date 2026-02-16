package com.hanu.cloudkitchen.service.imp;

import com.hanu.cloudkitchen.DTO.CategoryRequest;
import com.hanu.cloudkitchen.DTO.CategoryResponse;
import com.hanu.cloudkitchen.entity.Category;
import com.hanu.cloudkitchen.entity.Product;
import com.hanu.cloudkitchen.repository.CategoryRepo;
import com.hanu.cloudkitchen.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {

    private final CategoryRepo categoryRepo;

    public CategoryServiceImp(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    // -------- CREATE --------

    @Override
    public CategoryResponse create(CategoryRequest request) {

        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        Category savedCategory = categoryRepo.save(category);

        return mapToResponse(savedCategory);
    }

    // -------- GET ALL --------

    @Override
    public List<CategoryResponse> findAll() {

        return categoryRepo.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // -------- MAPPER --------

    private CategoryResponse mapToResponse(Category category) {

        List<Long> productIds = category.getProducts() == null
                ? List.of()
                : category.getProducts()
                .stream()
                .map(Product::getId)
                .toList();

        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getDescription(),
                productIds
        );
    }

}
