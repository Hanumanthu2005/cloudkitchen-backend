package com.hanu.cloudkitchen.repository;

import com.hanu.cloudkitchen.entity.Order;
import com.hanu.cloudkitchen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {

    List<Order> findByUser(User user);
}
