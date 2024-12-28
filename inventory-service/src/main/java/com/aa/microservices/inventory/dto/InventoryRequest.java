package com.aa.microservices.inventory.dto;

import com.aa.microservices.inventory.model.Inventory;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record InventoryRequest(

        Long id,
        @NotNull(message ="SkuCode Cannot be Null") @NotBlank(message ="SkuCode Cannot be Blank")
        String skuCode,
        @NotNull(message ="Quantity Cannot be Null") @Positive(message ="Qty must be > 0")
        Integer quantity) {

    public InventoryRequest convertToInventoryRequest(Inventory inventory) {
        return new InventoryRequest(inventory.getId(), inventory.getSkuCode(), inventory.getQuantity());
    }

    public Inventory convertToInventory() {
        return new Inventory(id, skuCode, quantity);
    }
}
