package com.aa.microservices.inventory.repository;

import com.aa.microservices.inventory.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

   boolean existsBySkuCodeAndQuantityGreaterThanEqual(String skuCode, Integer quantity);
}
