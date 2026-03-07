package com.techie.order_service.producer;

import com.techie.order_service.event.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderProducer {

    private final KafkaTemplate<String, OrderCreatedEvent> kafkaTemplate;

    public void sendOrderEvent(OrderCreatedEvent event) {

        kafkaTemplate.send("order-created-topic", event);

        System.out.println("Order event published");
    }
}