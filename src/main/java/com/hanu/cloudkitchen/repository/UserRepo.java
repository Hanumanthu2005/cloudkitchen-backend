package com.hanu.cloudkitchen.repository;

import com.hanu.cloudkitchen.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User, Long> {
}
