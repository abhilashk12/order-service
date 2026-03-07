package com.techie.order_service.consumer;

import com.techie.order_service.entity.Order;
import com.techie.order_service.event.InventoryUpdatedEvent;
import com.techie.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InventoryConsumer {

    private final OrderRepository orderRepository;

    @KafkaListener(
            topics = "inventory-updated-topic",
            groupId = "order-group"
    )
    public void consume(InventoryUpdatedEvent event) {

        Order order =
                orderRepository.findById(event.getOrderId())
                        .orElseThrow();

        order.setStatus(event.getStatus());

        orderRepository.save(order);

        System.out.println("Order status updated: " + event.getStatus());
    }
}