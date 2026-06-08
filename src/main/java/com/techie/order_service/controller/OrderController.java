package com.techie.order_service.controller;

import com.techie.order_service.dto.OrderRequest;
import com.techie.order_service.dto.OrderResponse;
import com.techie.order_service.entity.Order;
import com.techie.order_service.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(@RequestBody OrderRequest request) {

        return ResponseEntity.ok(orderService.placeOrder(request));
    }

    @GetMapping("/getAllOrders")
    public ResponseEntity<List<Order>> getAllorders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }

    @GetMapping("/{name}")
    public ResponseEntity<Order> getOrderByName(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}