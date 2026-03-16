package com.techie.order_service.controller;

import com.techie.order_service.dto.OrderRequest;
import com.techie.order_service.dto.OrderResponse;
import com.techie.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {

        return ResponseEntity.ok(orderService.placeOrder(request));
    }
}