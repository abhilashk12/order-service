package com.techie.order_service.service;

import com.techie.order_service.dto.OrderRequest;
import com.techie.order_service.dto.OrderResponse;

public interface OrderService {

    OrderResponse placeOrder(OrderRequest request);

}
