package com.hanu.cloudkitchen.DTO.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
public class OrderResponse {

    private Long orderId;
    private String status;
    private BigDecimal totalPrice;
    private List<OrderItemResponse> items;
}
