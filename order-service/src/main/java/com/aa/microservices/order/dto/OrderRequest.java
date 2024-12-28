package com.aa.microservices.order.dto;

import com.aa.microservices.order.model.Order;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import org.apache.logging.log4j.message.Message;

import java.math.BigDecimal;

public record OrderRequest(

        Long id,
        @NotNull(message ="Order Number Cannot be Null") @NotBlank(message = "Order Number cannot be Blank")
        String orderNumber,
        @NotNull(message ="SkuCode Cannot be Null") @NotBlank(message ="SkuCode Cannot be Blank")
        String skuCode,
        @NotNull(message ="Price Cannot be Null")  @Positive(message ="Price Cannot be > 0")
        BigDecimal price,
        @NotNull(message ="Quantity Cannot be Null") @Positive(message ="Qty must be > 0")
        Integer quantity) {

    public OrderRequest convertToOrderRequest(Order order) {
        return new OrderRequest(order.getId(),order.getOrderNumber(), order.getSkuCode(), order.getPrice(), order.getQuantity());
    }

    public Order convertToOrder() {
        return new Order(id,orderNumber, skuCode, price, quantity);
    }
}
