package com.hanu.cloudkitchen.repository;

import com.hanu.cloudkitchen.entity.Category;
import com.hanu.cloudkitchen.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findByCategory(Category category);
}
