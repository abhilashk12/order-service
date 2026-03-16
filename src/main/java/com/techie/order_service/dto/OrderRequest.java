package com.techie.order_service.dto;

import lombok.Data;

@Data
public class OrderRequest {

    private Long productId;
    private Integer quantity;
}