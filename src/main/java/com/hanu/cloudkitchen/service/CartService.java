package com.hanu.cloudkitchen.service;

import com.hanu.cloudkitchen.DTO.response.CartResponse;

public interface CartService {

    CartResponse getCart(Long userId);

    CartResponse addToCart(Long userId, Long productId, Integer quantity);

    CartResponse removeFromCart(Long userId, Long productId);

    void clearCart(Long userId);
}

