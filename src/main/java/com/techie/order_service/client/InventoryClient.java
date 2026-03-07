package com.techie.order_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryClient {

    @GetMapping("/inventory/check")
    Boolean checkStock(
            @RequestParam Long productId,
            @RequestParam Integer quantity
    );

    @PostMapping("/inventory/reduce")
    String reduceStock(
            @RequestParam Long productId,
            @RequestParam Integer quantity
    );
}