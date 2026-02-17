package com.hanu.cloudkitchen.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CartItemResponse {

    private Long productId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
}
