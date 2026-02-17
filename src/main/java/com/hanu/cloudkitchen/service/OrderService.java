package com.hanu.cloudkitchen.service;

import com.hanu.cloudkitchen.DTO.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(Long userId);

    List<OrderResponse> getUserOrders(Long userId);
}

