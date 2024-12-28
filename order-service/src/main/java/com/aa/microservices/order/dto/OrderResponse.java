package com.aa.microservices.order.dto;

import com.aa.microservices.order.model.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record OrderResponse(

        Long id,
        @NotNull(message ="Name Cannot be Null") @NotBlank(message = "Name cannot be Blank")
        String orderNumber,
        @NotNull(message ="SkuCode Cannot be Null") @NotBlank(message ="SkuCode Cannot be Blank")
        String skuCode,
        @NotNull(message ="Price Cannot be Null")  @Positive(message ="Price Cannot be > 0")
        BigDecimal price,
        @NotNull(message ="Quantity Cannot be Null") @Positive(message ="Qty must be > 0")        Integer quantity) {

    public OrderResponse convertToOrderResponse(Order order) {
        return new OrderResponse(order.getId(),order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity());
    }

    public Order convertToOrder() {
        return new Order(id,orderNumber, skuCode, price, quantity);
    }
}
