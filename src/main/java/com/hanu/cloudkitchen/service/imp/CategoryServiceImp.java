package com.hanu.cloudkitchen.service.imp;

import com.hanu.cloudkitchen.entity.Category;
import com.hanu.cloudkitchen.repository.CategoryRepo;
import com.hanu.cloudkitchen.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImp implements CategoryService {

    private CategoryRepo categoryRepo;

    public CategoryServiceImp(CategoryRepo categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    @Override
    public Category create(Category category) {
        return categoryRepo.save(category);
    }
}
