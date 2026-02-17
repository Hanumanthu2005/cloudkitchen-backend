package com.hanu.cloudkitchen.service.imp;

import com.hanu.cloudkitchen.DTO.response.OrderItemResponse;
import com.hanu.cloudkitchen.DTO.response.OrderResponse;
import com.hanu.cloudkitchen.entity.Cart;
import com.hanu.cloudkitchen.entity.Order;
import com.hanu.cloudkitchen.entity.OrderItem;
import com.hanu.cloudkitchen.entity.User;
import com.hanu.cloudkitchen.repository.CartRepo;
import com.hanu.cloudkitchen.repository.OrderRepo;
import com.hanu.cloudkitchen.repository.UserRepo;
import com.hanu.cloudkitchen.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
@Transactional
public class OrderServiceImp implements OrderService {

    private final OrderRepo orderRepo;
    private final CartRepo cartRepo;
    private final UserRepo userRepo;

    public OrderServiceImp(OrderRepo orderRepo,
                           CartRepo cartRepo,
                           UserRepo userRepo) {
        this.orderRepo = orderRepo;
        this.cartRepo = cartRepo;
        this.userRepo = userRepo;
    }

    @Override
    public OrderResponse placeOrder(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        if (cart.getItems().isEmpty()) {
            throw new IllegalArgumentException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(user);

        List<OrderItem> orderItems = cart.getItems()
                .stream()
                .map(cartItem -> {
                    OrderItem orderItem = new OrderItem();
                    orderItem.setOrder(order);
                    orderItem.setProduct(cartItem.getProduct());
                    orderItem.setQuantity(cartItem.getQuantity());
                    orderItem.setPrice(cartItem.getProduct().getPrice()); // snapshot
                    return orderItem;
                })
                .toList();

        order.setOrderItems(orderItems);

        BigDecimal total = orderItems.stream()
                .map(item ->
                        item.getPrice().multiply(
                                BigDecimal.valueOf(item.getQuantity())
                        )
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);

        Order savedOrder = orderRepo.save(order);

        // Clear cart
        cart.getItems().clear();

        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getUserOrders(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return orderRepo.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private OrderResponse mapToResponse(Order order) {

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(item -> new OrderItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getPrice()
                ))
                .toList();

        return new OrderResponse(
                order.getId(),
                order.getStatus().name(),
                order.getTotalPrice(),
                items
        );
    }
}

