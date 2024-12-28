package com.aa.microservices.inventory.service;

import com.aa.microservices.inventory.repository.InventoryRepository;
import com.aa.microservices.inventory.dto.InventoryRequest;
import com.aa.microservices.inventory.dto.InventoryResponse;
import com.aa.microservices.inventory.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;


    public boolean isInStock(String skuCode, Integer quantity) {
        return inventoryRepository.existsBySkuCodeAndQuantityGreaterThanEqual(skuCode, quantity);
    }

    public InventoryResponse createInventory(InventoryRequest inventoryRequest) {

        Inventory inventory = inventoryRepository.save(inventoryRequest.convertToInventory());
        return new InventoryResponse(inventory.getId(), inventory.getSkuCode(),  inventory.getQuantity());
    }

    public InventoryResponse getInventory(Long inventoryId) {
        Inventory inventory = inventoryRepository.findById(inventoryId).orElseThrow(() -> new RuntimeException("No Item in Inventory found"));
        return new InventoryResponse(inventory.getId(), inventory.getSkuCode(),  inventory.getQuantity());
    }

    public List<InventoryResponse> getInventories(){


        return inventoryRepository.findAll().stream()
                .map(inventory -> new InventoryResponse(inventory.getId(),inventory.getSkuCode(),  inventory.getQuantity()))
                .collect(Collectors.toList());
    }

}
