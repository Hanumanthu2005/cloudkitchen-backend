package com.hanu.cloudkitchen.repository;

import com.hanu.cloudkitchen.entity.Cart;
import com.hanu.cloudkitchen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart, Long> {

    Optional<Cart> findByUser(User user);
}
