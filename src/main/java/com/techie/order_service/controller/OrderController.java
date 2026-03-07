package com.techie.order_service.controller;

import com.techie.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(
            @RequestParam Long productId,
            @RequestParam Integer quantity) {

        return ResponseEntity.ok(
                orderService.placeOrder(productId, quantity)
        );
    }
}