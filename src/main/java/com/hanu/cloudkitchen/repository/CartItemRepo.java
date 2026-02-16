package com.hanu.cloudkitchen.repository;

import com.hanu.cloudkitchen.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}
