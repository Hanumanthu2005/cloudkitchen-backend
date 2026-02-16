package com.hanu.cloudkitchen.service.imp;

import com.hanu.cloudkitchen.DTO.CartItemResponse;
import com.hanu.cloudkitchen.DTO.CartResponse;
import com.hanu.cloudkitchen.entity.Cart;
import com.hanu.cloudkitchen.entity.CartItem;
import com.hanu.cloudkitchen.entity.Product;
import com.hanu.cloudkitchen.entity.User;
import com.hanu.cloudkitchen.repository.CartItemRepo;
import com.hanu.cloudkitchen.repository.CartRepo;
import com.hanu.cloudkitchen.repository.ProductRepo;
import com.hanu.cloudkitchen.repository.UserRepo;
import com.hanu.cloudkitchen.service.CartService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartServiceImp implements CartService {

    private final CartRepo cartRepo;
    private final CartItemRepo cartItemRepo;
    private final ProductRepo productRepo;
    private final UserRepo userRepo;

    public CartServiceImp(CartRepo cartRepo,
                          CartItemRepo cartItemRepo,
                          ProductRepo productRepo,
                          UserRepo userRepo) {
        this.cartRepo = cartRepo;
        this.cartItemRepo = cartItemRepo;
        this.productRepo = productRepo;
        this.userRepo = userRepo;
    }

    @Override
    public CartResponse getCart(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> createCart(user));

        return mapToResponse(cart);
    }

    @Override
    public CartResponse addToCart(Long userId, Long productId, Integer quantity) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseGet(() -> createCart(user));

        Optional<CartItem> existingItem = cart.getItems()
                .stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(
                    existingItem.get().getQuantity() + quantity
            );
        } else {
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(quantity);
            item.setCart(cart);
            cart.getItems().add(item);
        }

        return mapToResponse(cart);
    }

    @Override
    public CartResponse removeFromCart(Long userId, Long productId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().removeIf(item ->
                item.getProduct().getId().equals(productId)
        );

        return mapToResponse(cart);
    }

    @Override
    public void clearCart(Long userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        cart.getItems().clear();
    }

    private Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        cart.setItems(new ArrayList<>());   // ADD THIS
        return cartRepo.save(cart);
    }

    private CartResponse mapToResponse(Cart cart) {

        List<CartItem> cartItems = cart.getItems() == null
                ? List.of()
                : cart.getItems();

        List<CartItemResponse> items = cartItems.stream()
                .map(item -> new CartItemResponse(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()
                ))
                .toList();

        BigDecimal total = items.stream()
                .map(i -> i.getPrice()
                        .multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(items, total);
    }

}
