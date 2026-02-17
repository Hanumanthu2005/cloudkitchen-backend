package com.hanu.cloudkitchen.controller;

import com.hanu.cloudkitchen.DTO.response.OrderResponse;
import com.hanu.cloudkitchen.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/{userId}/place")
    public ResponseEntity<OrderResponse> placeOrder(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.placeOrder(userId)
        );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<OrderResponse>> getUserOrders(
            @PathVariable Long userId) {

        return ResponseEntity.ok(
                orderService.getUserOrders(userId)
        );
    }
}
