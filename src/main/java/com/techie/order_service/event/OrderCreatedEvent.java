package com.techie.order_service.event;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreatedEvent {

    @Id
    private Long orderId;
    private Long productId;
    private Integer quantity;
}
