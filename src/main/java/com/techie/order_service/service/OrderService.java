package com.techie.order_service.service;

import com.techie.order_service.dto.OrderRequest;
import com.techie.order_service.dto.OrderResponse;
import com.techie.order_service.entity.Order;

import java.util.List;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

    List<Order> getAllOrders();

    public Order getOrderById(Long id);
}
