package com.aa.microservices.inventory.controller;

import com.aa.microservices.inventory.dto.InventoryRequest;
import com.aa.microservices.inventory.dto.InventoryResponse;
import com.aa.microservices.inventory.service.InventoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    //Prefer to use ResponseEntity instead of @ResponseStatus
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        return inventoryService.isInStock( skuCode,  quantity);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse createOrder(@Valid @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.createInventory(inventoryRequest);
    }

    @GetMapping(value="/{inventoryId}")
    public InventoryResponse getInventory(@Valid @PathVariable Long inventoryId) {
        return inventoryService.getInventory(inventoryId);
    }

    @GetMapping(value="/all")
    public List<InventoryResponse> getInventories() {
        return inventoryService.getInventories();
    }
}
