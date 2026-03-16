package com.techie.order_service.service;

import com.techie.order_service.dto.OrderRequest;
import com.techie.order_service.dto.OrderResponse;
import com.techie.order_service.entity.Order;
import com.techie.order_service.event.OrderCreatedEvent;
import com.techie.order_service.producer.OrderProducer;
import com.techie.order_service.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
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
    @CircuitBreaker(name = "inventoryService", fallbackMethod = "fallbackOrder")
    @Retry(name = "inventoryService")
    @Override
    public OrderResponse placeOrder(OrderRequest request) {

        log.info("Placing order for product {}", request.getProductId());

        Order order = Order.builder()
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .status("PENDING")
                .createdAt(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        OrderCreatedEvent event =
                new OrderCreatedEvent(
                        order.getId(),
                        order.getProductId(),
                        order.getQuantity()
                );

        orderProducer.sendOrderEvent(event);

        return map(order);
    }

    private OrderResponse map(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .productId(order.getProductId())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .build();
    }

    public String fallbackOrder(Long productId, Integer quantity, Exception ex) {

        return "Inventory service unavailable";
    }
}
