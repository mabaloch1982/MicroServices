package com.aa.microservices.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="inventory-service", url="${inventory.uri}")
public interface InventoryClient {

    @RequestMapping(method= RequestMethod.GET, value="/api/inventory")
    boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity);


}
