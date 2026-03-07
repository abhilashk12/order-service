package com.techie.order_service.service;

import com.techie.order_service.client.InventoryClient;
import com.techie.order_service.entity.Order;
import com.techie.order_service.event.OrderCreatedEvent;
import com.techie.order_service.producer.OrderProducer;
import com.techie.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final OrderProducer orderProducer;

    //this is normal synchronous microservice code
//    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackOrder")
//    public String placeOrder(Long productId, Integer quantity) {
//
//        Boolean inStock = inventoryClient.checkStock(productId, quantity);
//
//        if (!inStock) {
//            return "Out of stock";
//        }
//
//        inventoryClient.reduceStock(productId, quantity);
//
//        Order order = Order.builder()
//                .productId(productId)
//                .quantity(quantity)
//                .status("CREATED")
//                .build();
//
//        orderRepository.save(order);
//
//        return "Order placed successfully";
//    }
//
//    public String fallbackOrder(Long productId, Integer quantity, Exception ex) {
//        return "Inventory service unavailable. Please try later.";
//    }

    //this is Orchestration Saga
//    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackOrder1")
//    public String placeOrder(Long productId, Integer quantity) {
//
//        Order order = Order.builder()
//                .productId(productId)
//                .quantity(quantity)
//                .status("PENDING")
//                .build();
//
//        orderRepository.save(order);
//
//        Boolean inStock = inventoryClient.checkStock(productId, quantity);
//
//        if (!inStock) {
//            order.setStatus("FAILED");
//            orderRepository.save(order);
//            return "Out of stock";
//        }
//
//        inventoryClient.reduceStock(productId, quantity);
//
//        order.setStatus("CONFIRMED");
//        orderRepository.save(order);
//
//        return "Order confirmed";
//    }
//    public String fallbackOrder1(Long productId, Integer quantity, Exception ex) {
//        return "Inventory unavailable. Order will be processed later.";
//    }

    //this is Choreographic Saga(event driven)
    public String placeOrder(Long productId, Integer quantity) {

        Order order = Order.builder()
                .productId(productId)
                .quantity(quantity)
                .status("PENDING")
                .build();

        orderRepository.save(order);

        OrderCreatedEvent event =
                new OrderCreatedEvent(order.getId(), productId, quantity);

        orderProducer.sendOrderEvent(event);

        return "Order placed. Waiting for inventory confirmation.";

    }
}
