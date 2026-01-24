package com.pos.payload.dto;

import com.pos.model.Order;
import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class OrderItemDTO {
    private Long id;

    private Integer quantity;

    private Double price;

    private ProductDTO product;

    private Long productId;

    private Long  orderId;

    private Order order;
}
